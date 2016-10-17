package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Yamada on 2016/10/10.
 */
public class recipeTodayActivity extends Activity {

    /* TODO
     * 必要な項目の作成
     * レシピID
     * レシピ名表示ラベル
     * 材料表示ラベル(List)
     * 手順表示ラベル(List)
     * 何人前表示ラベル
     * ジャンル表示ラベル
     * カロリー表示ラベル
     * 価格表示ラベル
     * 画像表示imageView
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_today);

        /* TODO
         * それぞれのラベルなどのidを取得する。
         */

        /* TODO
         * 今日のレシピIDからサーバーにレシピデータを送ってもらうように要求。
         * もらったJSONファイルを TRecipeクラスのインスタンスに変換する or そのままそれぞれのラベルに代入する。
         * TRecipeインスタンスから項目をもらって、ラベルに表示する。
         */

        //引数にラベルを送る or 今日のレシピデータを受けっとってActivityでセットする
        TAsyncTodayRecipe asyncTodayRecipe = new TAsyncTodayRecipe(this);
        asyncTodayRecipe.execute();


    }

    /**
     * レシピの評価をするボタンを押した時の動作。評価画面に遷移
     * @param view
     */
    public void onClickMoveToEvaluate(View view){
        Intent intent = new Intent(this, evaluateActivity.class);
        startActivityForResult(intent, 0);
        /* TODO
         * レシピIDを送る
         */
    }
}
