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
    RatingBar fRatingBar;
    //もう作らない
    CheckBox fCheckBox;
    //作った作らなかった
    RadioGroup fRadioGroup;

    int fRequestId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate);

        //リクエストしてもらう日数を取得する
        fRequestId = (int) getIntent().getSerializableExtra("Request_Id");

        fRatingBar = (RatingBar)findViewById(R.id.ratingBar);
        fCheckBox = (CheckBox)findViewById(R.id.checkBox2);
        fRadioGroup = (RadioGroup)findViewById(R.id.radioButton_group_make);

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

        System.out.println(fRatingBar.getRating());
        System.out.println(fCheckBox.isChecked());
        System.out.println(fRadioGroup.getCheckedRadioButtonId());

        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 0);

    }

    /* TODO
     * ラジオグループのリスナーを作る
     */




}
