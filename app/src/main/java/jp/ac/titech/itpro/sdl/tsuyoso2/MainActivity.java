package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

     /* TODO
     * カレンダーのある日をクリックするとその日のレシピを表示するクリックリスナーの作成
     */


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
        String dateStr = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate = simpleDateFormat.parse(dateStr);

        //Intentにデータをつけて送る
        Intent intent = new Intent(this, recipeTodayActivity.class);
        intent.putExtra("Request_Date",requestDate);
        startActivity(intent);
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
