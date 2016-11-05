package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Yamada on 2016/10/10.
 */
public class shoppingListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otsukai_list);


        //ローカルDBから日付を受け取る
        Calendar calendar = Calendar.getInstance();
        String dateStr = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date requestDate = null;

        try {
            requestDate = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ListView shoppingListView = (ListView)findViewById(R.id.otsukai_list);
        ArrayList<String> shoppingList = new ArrayList<>();
        TAsyncShoppingList asyncJson = new TAsyncShoppingList(this, shoppingListView, shoppingList, requestDate);
        asyncJson.execute();

    }

}
