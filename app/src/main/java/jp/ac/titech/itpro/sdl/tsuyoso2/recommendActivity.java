package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Yamada on 2016/10/10.
 */
public class recommendActivity extends Activity {

    /* TODO
     * 提案してもらう日を設定するためのカレンダーもしくはDatePickerを追加
     * 提案してもらう日付と日数を保存するための項目の追加
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_auto);

        /* TODO
         * カレンダー,DatePickerのidを取得
         */
    }

    /* TODO
     * カレンダーもしくはDatePickerのリスナーを作る
     * 提案してもらう日付の取得と日数の取得を行う。
     */


    /**
     * 自動提案してもらう日を指定し、レシピリストを表示するために画面遷移
     * @param view
     */
    public void onClickMoveToRecipeList(View view){
        Intent intent = new Intent(this, recipeListActivity.class);
        startActivityForResult(intent, 0);
        /* TODO
         * 提案してもらう日付と日数を送る
         */
    }
}
