package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Tanaka on 2016/11/16.
 */
public class TAsyncRecommendImage extends AsyncTask<String, Integer, Bitmap> {

    private Activity fActivity;
    private ProgressDialog progressDialog;

    TRecommend fRecommend;
    int fRequestId;

    /**
     * コンストラクタ
     * @param activity
     */
    public TAsyncRecommendImage(Activity activity, TRecommend recommend){
        this.fActivity = activity;
        fRecommend = recommend;
        fRequestId = recommend.getRecpeId();
        System.out.println("TAsyncRecommendImage 起動");
    }

    /**
     * doInBackgroundの前処理
     */
    @Override
    protected void onPreExecute() {
        //doInBackGroundの前処理.
        System.out.println("onPreExecute");
        super.onPreExecute();

//        progressDialog = new ProgressDialog(fActivity);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();
    }

    /**
     * バックグラウンドで実行する処理
     * @param params
     * @return
     */
    @Override
    protected Bitmap doInBackground(String... params) {

        System.out.println("doInBackGround");
        System.out.println("TAsyncRecommendImage background");


        HttpURLConnection connection = null;
        URL url = null;

        Bitmap bitmap;
        String urlString = "http://160.16.213.209:8080/api/image/" + fRequestId;
//        String urlString = "http://160.16.213.209:8080/server/image/1.jpg";
        System.out.println(urlString);
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

            //publishProgress();

            final int status = connection.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK){
                System.out.println("connection OK");
                InputStream inputStream = connection.getInputStream();

                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }

        }
        catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 実行中
     * @param progress
     */
    public void progressUpdate(Integer... progress) {
        //progressDialog.incrementProgressBy(progress[0]);
    }

    /**
     * DoInBackgroundの後処理
     * @param bitmap
     */
    @Override
    protected void onPostExecute(Bitmap bitmap){

        System.out.println("onPostExecute");
        System.out.println("TAsyncImage post");

        // doInBackground後処理

        if(bitmap != null) {
            this.fRecommend.setImageBitMap(bitmap);
        }else {
            System.out.println("bitmap is null");
            Toast.makeText(fActivity, "サーバーと通信できません.", Toast.LENGTH_SHORT).show();
        }
        //ダイアログ消す
        //progressDialog.dismiss();
        ListView recipeListView = (ListView)fActivity.findViewById(R.id.recommend_list);

        recipeListView.invalidateViews();
    }

}
