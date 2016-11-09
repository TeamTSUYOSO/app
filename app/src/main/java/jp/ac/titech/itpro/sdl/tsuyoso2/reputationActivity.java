package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Yamada on 2016/11/09.
 */
public class reputationActivity extends Activity{

    LinearLayout fReputationLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reputation_layout);

        fReputationLayout = (LinearLayout) findViewById(R.id.reputation_linear_layout);

        //TODO 過去の評価を全て表示する
        for(int i = 0; i < 10; i++) {
            View reputationView = this.getLayoutInflater().inflate(R.layout.row_one_reputation, null);
            fReputationLayout.addView(reputationView);
            TextView dateTextView = (TextView) reputationView.findViewById(R.id.reputation_recipe_date);
            dateTextView.setText("aaa");

            TextView recipeNameTextView = (TextView) reputationView.findViewById(R.id.reputation_recipe_name);
            recipeNameTextView.setText("bbb");

            TextView reputationTextView = (TextView) reputationView.findViewById(R.id.reputation_star);
            reputationTextView.setText(i + "");
        }
    }
}
