package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import jp.ac.titech.itpro.sdl.tsuyoso2.Calendar.OnDateClickListener;
import jp.ac.titech.itpro.sdl.tsuyoso2.DB.LocalDatabaseService;

/**
 * Created by Yamada on 2016/10/10.
 */
public class recommendActivity extends Activity implements OnDateClickListener{

    private LocalDatabaseService dbs;

    ArrayList<String> selectedDates;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbs = new LocalDatabaseService(this);
        selectedDates = new ArrayList<String>();

        setContentView(R.layout.recommend_auto);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    /**
     * 自動提案してもらう日を指定し、レシピリストを表示するために画面遷移
     * @param view
     */
    public void onClickMoveToRecipeList(View view) throws ParseException {
        //Intentにデータをつけて送る
        if(selectedDates.size() > 0) {
            Collections.sort(selectedDates);
            Intent intent = new Intent(this, recipeListActivity.class);
            intent.putExtra("request_num", selectedDates.size());
            intent.putExtra("selectedDates", selectedDates);
            startActivity(intent);
        }

    }

    // カレンダー日付クリック時の処理
    @Override
    public void onDateClick(View dayView, int year, int month, int day) throws ParseException {
        //日付選択状態の制御
        String dateStr = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);

        if(!dayView.isSelected()){
            if(dbs.getRecipeIdByDate(dateStr) == -1){
                //レシピが未設定なら 未選択 -> 選択
                dayView.setSelected(true);
                selectedDates.add(dateStr);
            }
        }else{
            dayView.setSelected(false);
            selectedDates.remove(dateStr);
        }
    }
}
