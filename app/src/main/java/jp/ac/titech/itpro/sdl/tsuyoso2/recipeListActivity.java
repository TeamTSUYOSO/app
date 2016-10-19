package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Yamada on 2016/10/10.
 */
public class recipeListActivity extends Activity {

    /* TODO
     * ListViewによるレシピリストの生成
     * ListViewに入れるArrayAdapterの作成
     * 提案してもらう日付と日数を保存する項目の作成
     * 受け取るレシピリストの入れ物の作成
     *
     */

    int fRequestCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

        /* TODO
         * ListViewのidを取得
         * ListViewに入れるArrayAdapterの設定
         */

        /* TODO
         * 提案してもらう日付とレシピ数をrecommendActivityから受け取る。
         * サーバにそれを送ってレシピリストを取得する。
         * 受け取ったレシピリストをListViewに追加する。
         */

        //リクエストしてもらう日数を取得する
        fRequestCount = (int)getIntent().getSerializableExtra("request_num");
//        System.out.println("requestNum = " + fRequestCount);

        ListView listView = (ListView)findViewById(R.id.listView2);

        TAsyncRecommend asyncJson = new TAsyncRecommend(this, listView, fRequestCount);
        asyncJson.execute();

    }

    /**
     * 提案されたレシピを決定する時の動作
     * @param view
     */
    public void onClickRecipeListEnterButton(View view){
        /* TODO
         * レシピリストのIDと名前をローカルDBに保存。
         */

        //メインアクティビティに遷移
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 0);
    }

    /**
     * 修正ボタンが押された時の動作
     * @param view
     */
    public void onClickRecipeListChangeButton(View view){

        //提案画面に戻す
        Intent intent = new Intent(this, recommendActivity.class);
        startActivityForResult(intent, 0);

    }
}
