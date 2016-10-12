package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.RatingBar;

/**
 * Created by Yamada on 2016/10/10.
 */
public class evaluateActivity extends Activity {
    //レート
    RatingBar ratingBar;
    //もう作らない
    CheckBox checkBox;
    //作った作らなかった
    RadioGroup radioGroup;

    /* TODO
     * 作った作らなかったをradipGroupに書き換え
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate);

        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        checkBox = (CheckBox)findViewById(R.id.checkBox2);

        /* TODO
         * ラジオグループのIDを取得
         *
         */
    }

    /**
     * 決定ボタンを押した時
     * @param view
     */
    public void onClickEnterButton(View view){
        /*TODO
         * 作った作らなかったのラジオボタンに応じて
         * 作った場合、レートともう作らないのチェックボックスをローカルDBに保存。
         * 作らなかった場合、何もせずに画面を今日のレシピに遷移。
         */

        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 0);

    }

    /* TODO
     * ラジオグループのリスナーを作る
     */




}
