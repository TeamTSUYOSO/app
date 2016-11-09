package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import jp.ac.titech.itpro.sdl.tsuyoso2.DB.LocalDatabaseService;

/**
 * Created by Yamada on 2016/10/10.
 */
public class evaluateActivity extends Activity {

    TextView evaluate_date;
    TextView recipe_name;
    TextView genre;

    //レート
    RatingBar fRatingBar;

    //作った作らなかった
    RadioGroup fRadioGroup;
    RadioButton fRadioButtonMake;
    RadioButton fRadioButtonUnMake;

    int fRequestId;
    String fRequestDate;
    String fRecipeName;
    String fRecipeGenre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate);

        //リクエストしてもらう日数を取得する
        fRequestId = (int) getIntent().getSerializableExtra("request_id");
        fRequestDate = (String) getIntent().getSerializableExtra("request_date");
        fRecipeName = (String) getIntent().getSerializableExtra("recipe_name");
        fRecipeGenre = (String) getIntent().getSerializableExtra("recipe_genre");

        evaluate_date = (TextView)findViewById(R.id.evaluate_hd_recipe_date);
        evaluate_date.setText(fRequestDate);

        recipe_name = (TextView)findViewById(R.id.evaluate_hd_recipe_name);
        recipe_name.setText(fRecipeName);

        genre =  (TextView)findViewById(R.id.evaluate_hd_recipe_genre);
        genre.setText(fRecipeGenre);

        fRatingBar = (RatingBar)findViewById(R.id.ratingBar);
        fRadioGroup = (RadioGroup)findViewById(R.id.radioButton_group_make);

        fRadioButtonMake = (RadioButton)findViewById(R.id.radioButton_make);
        fRadioButtonUnMake =(RadioButton)findViewById(R.id.radioButton_unmake);
    }

    /**
     * 決定ボタンを押した時
     * @param view
     */
    public void onClickEnterButton(View view){

        if(fRadioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "作ったか作らなかったか入力してね!", Toast.LENGTH_SHORT).show();
        }
        //作った場合
        else if(fRadioGroup.getCheckedRadioButtonId() == fRadioButtonMake.getId()) {

            //評価をDBに保存, レートはdouble型で0.5刻みのため, 2倍してintにキャスト
            LocalDatabaseService localDatabaseService = new LocalDatabaseService(getApplicationContext());
            localDatabaseService.updateData(fRequestId, fRecipeName, fRequestDate, (int)fRatingBar.getRating() * 2);

            Toast.makeText(this, "フィードバックを保存しました!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 0);
        }
        else {

            Toast.makeText(this, "また今度作ってね!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 0);
        }
    }


}
