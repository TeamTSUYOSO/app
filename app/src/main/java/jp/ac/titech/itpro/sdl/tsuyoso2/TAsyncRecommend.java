package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.ArrayList;

public class TAsyncRecommend extends AsyncTask<String, Integer, JSONArray> {

    private Activity fActivity;
    private ListView fListView;
    private ProgressDialog progressDialog;
    private int fRequestCount;
    private ArrayList<String> fRecipeList;
    /**
     * コンストラクタ
     * @param activity
     */
    public TAsyncRecommend(Activity activity, ListView listView, int requestCount, ArrayList<String> recipeList){
        this.fActivity = activity;
        fListView = listView;
        fRequestCount = requestCount;
        fRecipeList = recipeList;
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
//        String urlString = "http://api.atnd.org/events/?keyword=android&format=json";
        String urlString = "http://160.16.213.209:8080/api/recipes/suggest";
        String readData = "";

        JSONObject requestJson = new JSONObject();
        try {
            requestJson.put("request_num", fRequestCount);
//
//
//            ArrayList<Integer> list = new ArrayList();
//            list.add(1);
//            list.add(2);
//            list.add(3);
//            requestJson.put("past_recipe_ids", );


        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(requestJson.toString());


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

            /* TODO
             * 日数とこれまで作ったレシピのデータを送る
             */

            //データを送る場合は,BufferedWriterに書き込む
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
//            bufferedWriter.write(requestJson.toString());
//            bufferedWriter.write("{\"request_num\":" + fRequestCount + ", \"past_recipe_ids\":[1,2], \"reputations\":[{\"recipe_id\" : 10, \"value\" : 5, \"proposed_time\" : 1}, {\"recipe_id\" : 12, \"value\" : 3, \"proposed_time\" : 2}]}");
            bufferedWriter.write("{\"request_num\":" + fRequestCount + ", \"past_recipe_ids\":[], \"reputations\":[]}");
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
            /**
             * [{"recipeId":10,"name":"肉じゃが"},{"recipeId":12,"name":"カルボナーラ"},
             * {"recipeId":7,"name":"かぼちゃと豚肉の甘煮"},{"recipeId":4,"name":"白菜と豚肉の蒸し焼き"},
             * {"recipeId":18,"name":"釜玉うどん"},{"recipeId":13,"name":"肉豆腐"},
             * {"recipeId":16,"name":"ペペロンチーノ"},{"recipeId":11,"name":"青椒肉絲"},
             * {"recipeId":22,"name":"鶏の唐揚げ"},{"recipeId":20,"name":"トマトスープパスタ"}]
             */
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
        /* TODO
         * doInBackGround から受け渡されるjsonObjectをパースして、レシピリストにセットする
         */
        System.out.println("onPostExecute");
        if(jsonObject != null) {
            System.out.println(jsonObject.toString());
        }
        else System.out.println("jsonobj = null");
        // doInBackground後処理

        try {
            if(jsonObject != null) {

                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject temp = jsonObject.getJSONObject(i);
//                JSONObject event = temp.getJSONObject("recipe");
                    fRecipeList.add(temp.getString("name"));
                }

                /*TODO
                 jsonArray をローカルDBに保存する部分に渡す.
                 */
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //List用ArrayAdapterの生成
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(fActivity, android.R.layout.simple_list_item_multiple_choice, fRecipeList);
        fListView.setAdapter(arrayAdapter);

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
