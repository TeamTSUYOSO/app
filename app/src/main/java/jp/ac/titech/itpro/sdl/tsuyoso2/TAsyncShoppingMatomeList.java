package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jp.ac.titech.itpro.sdl.tsuyoso2.DB.LocalDatabaseService;

/**
 * Created by Yamada on 2016/10/17.
 */
public class TAsyncShoppingMatomeList extends AsyncTask<String, Integer, JSONArray> {

    private Activity fActivity;
    private ProgressDialog progressDialog;

    LinearLayout fHeaderView;
    ListView fShoppingListView;
    String dateFormat = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat;

    Calendar fCalendar;
    String fDateString;
    String fInputString;

    LocalDatabaseService fLocalDatabaseService;
    /**
     * コンストラクタ
     */
    public TAsyncShoppingMatomeList(Activity activity , LinearLayout headerView, ListView shoppingList){

        this.fActivity = activity;
        fHeaderView = headerView;
        fShoppingListView = shoppingList;

        //今日の日付を取得する
        fCalendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat(dateFormat);
        fDateString = simpleDateFormat.format(fCalendar.getTime());

        fLocalDatabaseService = new LocalDatabaseService(fActivity.getApplicationContext());

    }

    /**
     * doInBackgroundの前処理
     */
    @Override
    protected void onPreExecute() {

        System.out.println("onPreExecute");
        super.onPreExecute();
        // doInBackground前処理

        progressDialog = new ProgressDialog(fActivity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    /**
     * バックグラウンドで実行する処理
     * @param params
     * @return
     */
    @Override
    protected JSONArray doInBackground(String... params) {

        System.out.println("doInBackGround");

        HttpURLConnection connection = null;
        URL url = null;

        String urlString = "http://160.16.213.209:8080/api/lists/list";
        String readData = "";

        try{
            //URL生成
            url = new URL(urlString);
            //接続用HttpURLConnection生成
            connection = (HttpURLConnection)url.openConnection();
            //リクエストメソッドの設定
            //POSTするときはここをPOST
            connection.setRequestMethod("POST");
            //リダイレクトを自動で許可しない設定
            connection.setInstanceFollowRedirects(false);
            //URL接続からデータを読み取るにはtrue
            connection.setDoInput(true);
            // URL接続にデータを書き込む場合はtrue
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // 接続
            connection.connect();

            //データを送る場合は,BufferedWriterに書き込む
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

            //今日の日付から買い物リストを作るためのIDリストを作成
            //TODO::未来分全て送信しているため何日分かに設定する？ -> DBのSQL limit 7
            fInputString = fLocalDatabaseService.getShoppingList(fDateString);

            bufferedWriter.write(fInputString);
            bufferedWriter.close();
            publishProgress();

            final int status = connection.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK){
                System.out.println("connection OK");
                readData = readInputStream(connection);
            }

        }
        catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return new JSONArray(readData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 実行中
     * @param progress
     */
    public void progressUpdate(Integer... progress) {
        progressDialog.incrementProgressBy(progress[0]);
    }

    /**
     * DoInBackgroundの後処理
     * @param jsonObject
     */
    @Override
    protected void onPostExecute(JSONArray jsonObject){

        System.out.println("onPostExecute");
        // doInBackground後処理

        if(jsonObject != null) {
            try {
                //日付とIDのマップ, 日付も送る必要があるが、とりあえず帰ってくる材料リストがmapと同じ順序なので日付も順序通りに取得してる
                Map<String, String> map = fLocalDatabaseService.getShoppingMap(fDateString);
                Iterator iterator = map.keySet().iterator();
                //日付リスト
                Set<String> dateSet = map.keySet();
                //全てのレシピの材料リスト
                ArrayList<TShoppingItem> ingredientList = new ArrayList<>();
                //レシピ名リスト
                HashMap<String, String> recipeNameMap = new HashMap<String, String>();

                for(int i = 0; i < jsonObject.length(); i++){
                    //1レシピ
                    JSONObject temp = jsonObject.getJSONObject(i);
                    //レシピの全ての材料
                    JSONArray tempIngredients = temp.getJSONArray("ingredients");
                    //材料の日付を取得
                    String date = (String)iterator.next();
                    String recipe_name = temp.getString("name");
                    recipeNameMap.put(date, recipe_name);

                    for(int j = 0; j < tempIngredients.length(); j++) {
                        JSONObject ingredient = tempIngredients.getJSONObject(j);
                        String ingredient_name = ingredient.getString("name");
                        String quantity = ingredient.getString("quantity");

                        TShoppingItem item = new TShoppingItem(ingredient_name);
                        if(ingredientList.contains(item)){ //すでに材料があるなら追加
                            int position = ingredientList.indexOf(item);
                            ingredientList.get(position).addDateQuantity(date, quantity);
                        }
                        else { //材料がないなら新規作成
                            item.initQuantityMap(dateSet);
                            item.addDateQuantity(date, quantity);
                            ingredientList.add(item);
                        }
                    }

                }

                for(String date : dateSet){
                    try {
                        DateTime nowDateTime = new DateTime(simpleDateFormat.parse(date));
                        int month = nowDateTime.getMonthOfYear();
                        int day = nowDateTime.getDayOfMonth();

                        View dateView = fActivity.getLayoutInflater().inflate(R.layout.header_otsukai_date, fHeaderView, false);
                        TextView monthView = (TextView)dateView.findViewById(R.id.month);
                        monthView.setText(month + "");
                        TextView dayView = (TextView) dateView.findViewById(R.id.day);
                        dayView.setText(day + "");
                        TextView recipeNameView = (TextView) dateView.findViewById(R.id.recipe_name);
                        recipeNameView.setText(recipeNameMap.get(date));

                        fHeaderView.addView(dateView);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                //List用ArrayAdapterの生成
                TShoppingListArayAdapter arrayAdapter = new TShoppingListArayAdapter(fActivity, R.layout.row_shopping_item, ingredientList);
                fShoppingListView.setAdapter(arrayAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(fActivity, "サーバーと通信できません.", Toast.LENGTH_SHORT).show();
        }
        //ダイアログ消す
        progressDialog.dismiss();
    }

    /**
     * サーバーから帰ってきたデータの読み込み
     * @param connection
     * @return
     * @throws IOException
     */
    public String readInputStream(HttpURLConnection connection) throws IOException {

        InputStream inputStream = connection.getInputStream();
        StringBuffer stringBuffer = new StringBuffer();
        String string;

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while ((string = br.readLine()) != null) {
            stringBuffer.append(string);
        }

        try {
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();

    }
}
