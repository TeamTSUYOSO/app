package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Yamada on 2016/10/10.
 */
public class shoppingListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otsukai_list);

        LinearLayout header = (LinearLayout)findViewById(R.id.otsukai_list_header);
        for(int i = 0; i < 7; i++){
            View view = getLayoutInflater().inflate(R.layout.header_otsukai_date, header, false);
            TextView month = (TextView) view.findViewById(R.id.month);
            month.setText("12");
            TextView day = (TextView) view.findViewById(R.id.day);
            day.setText(i + "");
            header.addView(view);
        }

        ListView shoppingList = (ListView)findViewById(R.id.shopping_list_ingredients);
        TAsyncShoppingList asyncJson = new TAsyncShoppingList(this, shoppingList);
        asyncJson.execute();

    }

}
