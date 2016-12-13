package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import jp.ac.titech.itpro.sdl.tsuyoso2.DB.LocalDatabaseService;

/**
 * Created by Yamada on 2016/10/10.
 */
public class recipeTodayActivity extends Activity {

    //表示するレシピの日付
    String fRequestDate;
    //表示するレシピID
    int fRequestId;

    //画面部品
    TextView recipe_name;
    TextView serving_num;
    TextView cooking_time;
    TextView genre;
    TextView calorie;
    TextView price;
    ArrayList<String> instructionList;
    ArrayList<String> ingredientList;
    LinearLayout table_ingredients;
    LinearLayout table_instructions;
    ImageView recipe_image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_today);

        //リクエストしてもらう日数を取得する
        fRequestDate= (String) getIntent().getSerializableExtra("Request_Date");

        //ヘッダーに日付の表示
        TextView header_date = (TextView)findViewById(R.id.recipe_date);
        header_date.setText(fRequestDate);

        //日付からIDを取得する
        LocalDatabaseService localDatabaseService = new LocalDatabaseService(getApplicationContext());
        fRequestId = localDatabaseService.getRecipeIdByDate(fRequestDate);

        //レシピが提案されている時はサーバーと通信
        if(fRequestId != -1) {

            //セットするViewのIDを取得
            recipe_name = (TextView) findViewById(R.id.recipe_name);
            serving_num = (TextView) findViewById(R.id.recipe_serving_num);
            cooking_time = (TextView) findViewById(R.id.recipe_cooking_time);
            genre = (TextView) findViewById(R.id.recipe_genre);
            calorie = (TextView) findViewById(R.id.recipe_calorie);
            price = (TextView) findViewById(R.id.recipe_price);
            instructionList = new ArrayList<>();
            ingredientList = new ArrayList<>();
            table_ingredients = (LinearLayout) findViewById(R.id.table_ingredients);
            table_instructions = (LinearLayout) findViewById(R.id.table_instructions);
            recipe_image = (ImageView)findViewById(R.id.imageView);

            //引数にラベルを送る or 今日のレシピデータを受けっとってActivityでセットする
            TAsyncTodayRecipe asyncTodayRecipe = new TAsyncTodayRecipe(this, fRequestId, recipe_name, serving_num, cooking_time, genre, calorie, price,
                    table_instructions, instructionList, table_ingredients, ingredientList, recipe_image);
            asyncTodayRecipe.execute();

            //画像の表示
            TAsyncImage asyncImage = new TAsyncImage(this, recipe_image, fRequestId);
            asyncImage.execute();

        }
    }

    /**
     * レシピの評価をするボタンを押した時の動作。評価画面に遷移
     * @param view
     */
    public void onClickMoveToEvaluate(View view){

        if(fRequestId != -1) {
            Intent intent = new Intent(this, evaluateActivity.class);
            //その日のレシピの評価画面にレシピIDを送る
            intent.putExtra("request_id", fRequestId);
            intent.putExtra("request_date", fRequestDate);
            intent.putExtra("recipe_name", recipe_name.getText());
            intent.putExtra("recipe_genre", genre.getText());
            startActivity(intent);
        }

        //レシピが提案されてない時
        else{
            Toast.makeText(this, "レシピが提案されてないよ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 0);
        }

    }
}
