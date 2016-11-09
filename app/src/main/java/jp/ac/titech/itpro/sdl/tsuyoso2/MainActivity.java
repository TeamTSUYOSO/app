package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jp.ac.titech.itpro.sdl.tsuyoso2.DB.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    String dateFormat = "yyyy-MM-dd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * ローカルデータベースの初期化
         * データベースが存在しない場合はデータベース作成のSQLが投げられる
         */
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.close();

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.frag_calendar);

    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    /**
     * ボタン1のクリック、お使いリストへ遷移
     * @param view
     */
    public void onClickMoveToShoppingList(View view){
        //Intentにデータをつけて送る
        Intent intent = new Intent(this, shoppingListActivity.class);
        startActivityForResult(intent, 0);
    }

    /**
     * ボタン2のクリック、今日のレシピに遷移
     * @param view
     */
    public void onClickMoveToTodayRecipe(View view) throws ParseException {

        //今日の日付を呼び出し
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String requestDate = simpleDateFormat.format(calendar.getTime());

        //Intentにデータをつけて送る
        Intent intent = new Intent(this, recipeTodayActivity.class);
        intent.putExtra("Request_Date",requestDate);
        startActivity(intent);
    }

    /**
     * ボタン3のクリック、レシピ評価一覧に遷移
     * @param view
     */
    public void onClickMoveToReputation(View view){
        Intent intent = new Intent(this, reputationActivity.class);
        startActivityForResult(intent, 0);
    }

    /**
     * ボタン4のクリック、自動提案に遷移
     * @param view
     */
    public void onClickMoveToRecommend(View view){
        Intent intent = new Intent(this, recommendActivity.class);
        startActivityForResult(intent, 0);
    }

//    public void onClickShowDatabase(View view){
//        Intent intent = new Intent(this, DBActivity.class);
//        startActivityForResult(intent, 0);
//    }

}
