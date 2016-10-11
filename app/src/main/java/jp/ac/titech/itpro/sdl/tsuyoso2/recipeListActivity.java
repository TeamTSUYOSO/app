package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Yamada on 2016/10/10.
 */
public class recipeListActivity extends Activity {

    /* TODO
     * ListViewによるレシピリストの生成
     * ListViewに入れるArrayAdapterの作成
     *
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

        /* TODO
         * ListViewのidを取得
         * ListViewに入れるArrayAdapterの設定
         */
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
        /* TODO
         * 修正する項目を数えて再提案するようサーバーに投げる。
         * 受け取ってListViewの項目の書き換え。
         */

    }
}
