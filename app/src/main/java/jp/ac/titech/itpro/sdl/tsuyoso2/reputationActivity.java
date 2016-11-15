package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import jp.ac.titech.itpro.sdl.tsuyoso2.DB.LocalDatabaseService;

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

        LocalDatabaseService localDatabaseService = new LocalDatabaseService(getApplicationContext());
        JSONArray reputationArray = localDatabaseService.getAllEvaluations();

        if(reputationArray != null) {
            try {
                for (int i = 0; i < reputationArray.length(); i++) {
                    View reputationView = this.getLayoutInflater().inflate(R.layout.row_one_reputation, null);
                    fReputationLayout.addView(reputationView);
                    TextView dateTextView = (TextView) reputationView.findViewById(R.id.reputation_recipe_date);

                    dateTextView.setText(reputationArray.getJSONObject(i).getString("cook_date"));

                    TextView recipeNameTextView = (TextView) reputationView.findViewById(R.id.reputation_recipe_name);
                    recipeNameTextView.setText(localDatabaseService.getRecipeNameByDate(reputationArray.getJSONObject(i).getString("cook_date")));

                    TextView reputationTextView = (TextView) reputationView.findViewById(R.id.reputation_star);
                    reputationTextView.setText(reputationArray.getJSONObject(i).getString("evaluation"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            //評価なし
            Toast.makeText(this, "レシピの評価がありません", Toast.LENGTH_SHORT).show();
        }
    }
}
