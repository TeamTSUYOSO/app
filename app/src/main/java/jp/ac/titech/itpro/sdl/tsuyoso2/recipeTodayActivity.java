package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Yamada on 2016/10/10.
 */
public class recipeTodayActivity extends Activity {

    //表示するレシピの日付
    Date fRequestDate;
    //表示するレシピID
    int fRequestId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_today);

        //リクエストしてもらう日数を取得する
        fRequestDate= (Date) getIntent().getSerializableExtra("Request_Date");

        System.out.println(fRequestDate.toString());

        /* TODO
         * "fRequestId = getFromLocalDB;"
         */
        Random random = new Random();
        fRequestId = random.nextInt(23);

        //セットするViewのIDを取得
        TextView recipe_name = (TextView)findViewById(R.id.recipe_name);
//        TextView serving_num = (TextView)findViewById(R.id.recipe_serving_num);
        TextView cooking_time = (TextView)findViewById(R.id.recipe_cooking_time);
        TextView genre = (TextView)findViewById(R.id.recipe_genre);
        TextView calorie = (TextView)findViewById(R.id.recipe_calorie);
        TextView price = (TextView)findViewById(R.id.recipe_price);
        ListView instructions = (ListView)findViewById(R.id.recipe_instructions);
        ArrayList<String> instructionList = new ArrayList<>();
        ListView ingredients = (ListView)findViewById(R.id.recipe_ingredients);
        ArrayList<String> ingredientList = new ArrayList<>();

        //引数にラベルを送る or 今日のレシピデータを受けっとってActivityでセットする
        TAsyncTodayRecipe asyncTodayRecipe = new TAsyncTodayRecipe(this,fRequestId, recipe_name, /*serving_num,*/ cooking_time, genre, calorie, price,
                                                                    instructions, instructionList,ingredients,  ingredientList);
        asyncTodayRecipe.execute();


    }

    /**
     * レシピの評価をするボタンを押した時の動作。評価画面に遷移
     * @param view
     */
    public void onClickMoveToEvaluate(View view){


        Intent intent = new Intent(this, evaluateActivity.class);
        //その日のレシピの評価画面にレシピIDを送る
        intent.putExtra("Request_Id", fRequestId);
        startActivity(intent);

    }
}
