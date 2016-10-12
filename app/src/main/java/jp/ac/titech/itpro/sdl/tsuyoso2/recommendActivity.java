package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.NumberPicker;

/**
 * Created by Yamada on 2016/10/10.
 */
public class recommendActivity extends Activity {

    /* TODO
     * 提案してもらう日を設定するためのカレンダーもしくはDatePickerを追加
     * 提案してもらう日付と日数を保存するための項目の追加
     */

    NumberPicker numberPicker;
    DatePicker datePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_auto);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /* TODO
         * カレンダー,DatePickerのidを取得
         */

        setViews();
        initViews();

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

        //以下のメソッドで日付と日数を取得
        System.out.println(datePicker.getYear() + "/" + (datePicker.getMonth()+1) + "/" + datePicker.getDayOfMonth());
        System.out.println(numberPicker.getValue());
    }

    /**
     * 画面構成要素のIDの設定
     */
    private void setViews(){
        numberPicker = (NumberPicker)findViewById(R.id.numPicker);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
    }

    /**
     * NUmberPickerの値の設定
     */
    private void initViews(){
        //提案してもらう日数最大値、最小値の設定
        numberPicker.setMaxValue(7);
        numberPicker.setMinValue(1);
    }
}
