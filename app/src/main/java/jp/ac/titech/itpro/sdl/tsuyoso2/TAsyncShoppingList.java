package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jp.ac.titech.itpro.sdl.tsuyoso2.DB.LocalDatabaseService;

/**
 * Created by Yamada on 2016/10/17.
 */
public class TAsyncShoppingList extends AsyncTask<String, Integer, JSONArray> {

    private Activity fActivity;
    private ProgressDialog progressDialog;

    LinearLayout fShoppingListView;
    String dateFormat = "yyyy-MM-dd";

    Calendar fCalendar;
    String fDateString;
    String fInputString;

    LocalDatabaseService fLocalDatabaseService;
    /**
     * コンストラクタ
     * @param activity
     */
    public TAsyncShoppingList(Activity activity , LinearLayout shoppingList){

        this.fActivity = activity;
        fShoppingListView = shoppingList;

        //今日の日付を取得する
        fCalendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
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

                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject temp = jsonObject.getJSONObject(i);
                    JSONArray tempIngredients = temp.getJSONArray("ingredients");

                    View dateView = fActivity.getLayoutInflater().inflate(R.layout.row_shopping_list_date, null);
                    fShoppingListView.addView(dateView);
                    TextView dateTextView = (TextView)dateView.findViewById(R.id.shopping_list_recipe_date);
                    //idをもとにローカルDBから日付取得
                    dateTextView.setText(fLocalDatabaseService.getCookDateById(Integer.parseInt(temp.getString("recipeId"))));

                    TextView recipeNameView = (TextView)dateView.findViewById(R.id.shopping_list_recipe_name);
                    recipeNameView.setText(temp.getString("name"));

                    TextView servingNumTextView = (TextView)dateView.findViewById(R.id.shopping_list_recipe_serving_num);
                    servingNumTextView.setText(temp.getString("serving_num"));


                    for (int j = 0; j < tempIngredients.length(); j++) {
                        JSONObject ingredient = tempIngredients.getJSONObject(j);

                        View view = fActivity.getLayoutInflater().inflate(R.layout.row_ingredients, null);
                        fShoppingListView.addView(view);
                        TextView name = (TextView) view.findViewById(R.id.ingredient_name);
                        name.setText(ingredient.getString("name"));
                        TextView quantity = (TextView) view.findViewById(R.id.ingredient_quantity);
                        quantity.setText(ingredient.getString("quantity"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(fActivity, "サーバーと通信できません.", Toast.LENGTH_SHORT).show();
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
