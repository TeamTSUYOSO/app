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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Yamada on 2016/10/17.
 */
public class TAsyncShoppingList extends AsyncTask<String, Integer, JSONObject> {

    private Activity fActivity;
    private ProgressDialog progressDialog;

    ListView fIngredientsListView;
    ArrayList<String> fIngredientArrayList;
    Date fRecipeDate;
    String dateFormat = "yyyy-MM-dd";

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
    protected JSONObject doInBackground(String... params) {
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
        String urlString = "http://160.16.213.209:8080/api/recipe/" + fRequestId;
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

        System.out.println("onPostExecute");
//        System.out.println(jsonObject.toString());
        // doInBackground後処理

        if(jsonObject != null) {
            try {

                JSONArray ingredientsJsonArray = jsonObject.getJSONArray("ingredients");

                ArrayList<JSONObject> ingredientsArray = new ArrayList<>();
                for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                    ingredientsArray.add(ingredientsJsonArray.getJSONObject(i));
                }

                ArrayList<String> ingList = new ArrayList<>();

                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

                ingList.add(sdf.format(fRecipeDate));
                for(int i = 0; i < ingredientsArray.size(); i++){
                   ingList.add(ingredientsArray.get(i).getString("name") + " ・・・ " + ingredientsArray.get(i).getString("quantity"));
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(fActivity, android.R.layout.simple_list_item_1, ingList);
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
