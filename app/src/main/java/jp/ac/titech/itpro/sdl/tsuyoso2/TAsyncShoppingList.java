package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Yamada on 2016/10/17.
 */
public class TAsyncShoppingList extends AsyncTask<String, Integer, JSONArray> {

    private Activity fActivity;
    private ProgressDialog progressDialog;

    ListView fIngredientsListView;
    ArrayList<String> fIngredientArrayList;
    Date fRecipeDate;
    String dateFormat = "yyyy-MM-dd";

    //日付のリスト
    ArrayList<Date> fDateList;

    /**
     * コンストラクタ
     * @param activity
     */
    public TAsyncShoppingList(Activity activity , ListView ingredientsListView, ArrayList<String> ingredientArrayList, Date date){

        this.fActivity = activity;
        fIngredientsListView = ingredientsListView;
        fIngredientArrayList = ingredientArrayList;
        fRecipeDate = date;
    }

    /**
     * doInBackgroundの前処理
     */
    @Override
    protected void onPreExecute() {
        /* TODO
         * doInBackGroundの前処理.
         */

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
        /* TODO
         * Activityから受け渡されるデータをサーバに送って帰ってきたデータをリターンする
         * onPostExecuteに渡される.
         */
        System.out.println("doInBackGround");

        HttpURLConnection connection = null;
        URL url = null;

        /* TODO
         * fRequestIdはローカルDBから受け取ったものを送る
         */

        int fRequestId = 1;
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
//            bufferedWriter.write(requestJson.toString());
//            bufferedWriter.write("{\"request_num\":" + fRequestCount + ", \"past_recipe_ids\":[1,2], \"reputations\":[{\"recipe_id\" : 10, \"value\" : 5, \"proposed_time\" : 1}, {\"recipe_id\" : 12, \"value\" : 3, \"proposed_time\" : 2}]}");
            /* TODO
                ローカルDBから作ってないレシピ受け取って送る
             */
            bufferedWriter.write("{\"past_recipe_ids\":[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23]}");
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
//        System.out.println(jsonObject.toString());
        // doInBackground後処理

        if(jsonObject != null) {
            try {

                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject temp = jsonObject.getJSONObject(i);
                    JSONArray tempIngredients = temp.getJSONArray("ingredients");

                    fIngredientArrayList.add("//////////////////////////////////////" + temp.getString("name") + "//////////////////////////////////////");

                    if(tempIngredients != null) {
                        for (int j = 0; j < tempIngredients.length(); j++){
                            fIngredientArrayList.add(tempIngredients.getJSONObject(j).getString("name") + " ・・・" + tempIngredients.getJSONObject(j).getString("quantity"));
                        }
                    }
                }

                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
//                ingList.add(sdf.format(fRecipeDate));

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(fActivity, android.R.layout.simple_list_item_1, fIngredientArrayList);
                fIngredientsListView.setAdapter(arrayAdapter);

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
