package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Yamada on 2016/10/10.
 */
public class recipeTodayActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_today);
    }

    /**
     * イメージ1のクリック、評価へ遷移
     * @param view
     */
    public void onClickImageView2(View view){
        Intent intent = new Intent(this, evaluateActivity.class);
        startActivityForResult(intent, 0);
    }
}
