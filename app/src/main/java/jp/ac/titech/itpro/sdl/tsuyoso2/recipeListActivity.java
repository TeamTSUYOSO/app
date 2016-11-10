package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Yamada on 2016/10/10.
 */
public class recipeListActivity extends Activity {

    int fRequestCount;
    ArrayList<String> fSelectedDates;
    TAsyncRecommend asyncJson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

        //リクエストしてもらう日数を取得する
        fRequestCount = (int)getIntent().getSerializableExtra("request_num");
        fSelectedDates = getIntent().getStringArrayListExtra("selectedDates");

        ListView listView = (ListView)findViewById(R.id.recommend_list);

        ArrayList<TRecommend> recipeList = new ArrayList<>();
        asyncJson = new TAsyncRecommend(this, listView, fRequestCount, recipeList, fSelectedDates);
        asyncJson.execute();

    }

    /**
     * 提案されたレシピを決定する時の動作
     * @param view
     */
    public void onClickRecipeListEnterButton(View view){

        /* TODO
         * レシピリストのJSONArrayを受け取ってローカルDBに保存する
         */

        JSONArray recipeListJsonArray = asyncJson.getRecipeListJsonArray();


        //メインアクティビティに遷移
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 0);
    }

    /**
     * 修正ボタンが押された時の動作
     * @param view
     */
    public void onClickRecipeListChangeButton(View view){

        //提案画面に戻す
        Intent intent = new Intent(this, recommendActivity.class);
        startActivityForResult(intent, 0);

    }
}
