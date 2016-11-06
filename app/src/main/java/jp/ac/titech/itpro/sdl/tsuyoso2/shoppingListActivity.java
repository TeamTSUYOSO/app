package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * Created by Yamada on 2016/10/10.
 */
public class shoppingListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otsukai_list);

        LinearLayout shoppingList = (LinearLayout)findViewById(R.id.shopping_list_ingredients);
        TAsyncShoppingList asyncJson = new TAsyncShoppingList(this, shoppingList);
        asyncJson.execute();

    }

}
