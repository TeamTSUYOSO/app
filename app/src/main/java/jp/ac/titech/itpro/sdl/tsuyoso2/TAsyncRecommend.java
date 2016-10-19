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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TAsyncRecommend extends AsyncTask<String, Integer, JSONObject>{

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
    protected JSONObject doInBackground(String... params) {
        /* TODO
         * Activityから受け渡されるデータをサーバに送って帰ってきたデータをリターンする
         * onPostExecuteに渡される.
         */
        System.out.println("doInBackGround");

        HttpURLConnection connection = null;
        URL url = null;
        String urlString = "http://api.atnd.org/events/?keyword=android&format=json";
        String readData = "";

        try{
            //URL生成
            url = new URL(urlString);
            //接続用HttpURLConnection生成
            connection = (HttpURLConnection)url.openConnection();
            //リクエストメソッドの設定
            //POSTするときはここをPOST
            connection.setRequestMethod("GET");
            //リダイレクトを自動で許可しない設定
            connection.setInstanceFollowRedirects(false);
            //URL接続からデータを読み取るにはtrue
            connection.setDoInput(true);
            // URL接続にデータを書き込む場合はtrue
            connection.setDoOutput(false);

            /* TODO
             * 日数とこれまで作ったレシピのデータを送る
             */



            //データを送る場合は,BufferedWriterに書き込む
//            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
//            bufferedWriter.write("request_num=" + fRequestCount);
//            bufferedWriter.close();


            // 接続
            connection.connect();

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
            return new JSONObject(readData);
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
    protected void onPostExecute(JSONObject jsonObject){
        /* TODO
         * doInBackGround から受け渡されるjsonObjectをパースして、レシピリストにセットする
         */
        System.out.println("onPostExecute");
//        System.out.println(jsonObject.toString());
        // doInBackground後処理

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("events");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject temp = jsonArray.getJSONObject(i);
                JSONObject event = temp.getJSONObject("event");
                fRecipeList.add(event.getString("title"));
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
