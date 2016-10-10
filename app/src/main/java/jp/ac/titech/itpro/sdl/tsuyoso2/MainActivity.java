package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void onClickButton1(View view){
    }

    /**
     * ボタン2のクリック、今日のレシピ
     * @param view
     */
    public void onClickButton2(View view){
        Intent intent = new Intent(this, recipeTodayActivity.class);
        startActivityForResult(intent, 0);
    }

    /**
     * ボタン3のクリック、冷蔵庫
     * @param view
     */
    public void onClickButton3(View view){
    }

    /**
     * ボタン4のクリック、自動提案
     * @param view
     */
    public void onClickButton4(View view){
        Intent intent = new Intent(this, recommendActivity.class);
        startActivityForResult(intent, 0);
    }
}
