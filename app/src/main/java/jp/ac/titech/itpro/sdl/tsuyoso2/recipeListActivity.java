package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import jp.ac.titech.itpro.sdl.tsuyoso2.DB.LocalDatabaseService;

/**
 * Created by Yamada on 2016/10/10.
 */
public class recipeListActivity extends Activity {

    LocalDatabaseService dbs;

    TAsyncRecommend asyncJson;
    ListView recipeListView;

    int fRequestCount;
    ArrayList<String> fSelectedDates;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

        dbs = new LocalDatabaseService(this);
        //リクエストしてもらう日数を取得する
        fRequestCount = (int)getIntent().getSerializableExtra("request_num");
        fSelectedDates = getIntent().getStringArrayListExtra("selectedDates");

        recipeListView = (ListView)findViewById(R.id.recommend_list);

        ArrayList<TRecommend> recipeList = new ArrayList<>();
        asyncJson = new TAsyncRecommend(this, recipeListView, fRequestCount, recipeList, fSelectedDates);
        asyncJson.execute();

    }

    /**
     * 提案されたレシピのうち選択したものをDBに保存
     * @param view
     */
    public void onClickSaveSelectedRecipeButton(View view){
        SparseBooleanArray checked = recipeListView.getCheckedItemPositions();
        int size = checked.size();
        for (int i = 0; i < size; i++) {
            int key = checked.keyAt(i);
            boolean value = checked.get(key);
            if (value) {
                TRecommend recommend = (TRecommend)recipeListView.getItemAtPosition(key);
                dbs.saveSingleData(recommend.getRecpeId(),
                                   recommend.getRecpeName(),
                                   recommend.getDate(),
                                   0);
                Log.d("Save to DB", recommend.getDate() + " : " + recommend.getRecpeName());
            }
        }
        //メインアクティビティに遷移
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 0);
    }

    /**
     * 提案されたレシピ全てをDBに保存
     * @param view
     */
    public void onClickSaveAllRecipeButton(View view){
        for (int i = 0; i < recipeListView.getCount(); i++) {
            TRecommend recommend = (TRecommend)recipeListView.getItemAtPosition(i);
            dbs.saveSingleData(recommend.getRecpeId(),
                    recommend.getRecpeName(),
                    recommend.getDate(),
                    0);
            Log.d("Save to DB", recommend.getDate() + " : " + recommend.getRecpeName());
        }
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
