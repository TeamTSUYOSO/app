package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    /* TODO
     * カレンダー,DatePickerの表示
     * カレンダー送り、戻りボタンの作成
     * カレンダーのクリックイベント作成
     * 今日のレシピIDの取得
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

     /* TODO
     * カレンダーのある日をクリックするとその日のレシピを表示するクリックリスナーの作成
     */


    /**
     * ボタン1のクリック、お使いリストへ遷移
     * @param view
     */
    public void onClickMoveToShoppingList(View view){
        /* TODO
         * お使いリストの画面に遷移
         */
    }

    /**
     * ボタン2のクリック、今日のレシピに遷移
     * @param view
     */
    public void onClickMoveToTodayRecipe(View view){
        Intent intent = new Intent(this, recipeTodayActivity.class);
        startActivityForResult(intent, 0);

        /* TODO
         * 今日のレシピIDを取得して画面遷移とともに送る。
         */
    }

    /**
     * ボタン3のクリック、冷蔵庫に遷移
     * @param view
     */
    public void onClickMoveToRefrigerator(View view){
        /* TODO
         * 冷蔵庫の画面に遷移
         */
    }

    /**
     * ボタン4のクリック、自動提案に遷移
     * @param view
     */
    public void onClickMoveToRecommend(View view){
        Intent intent = new Intent(this, recommendActivity.class);
        startActivityForResult(intent, 0);
    }
}
