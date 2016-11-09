package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.ac.titech.itpro.sdl.tsuyoso2.DB.DatabaseHelper;
import jp.ac.titech.itpro.sdl.tsuyoso2.DB.LocalDatabaseService;

/**
 * Created by Mitsuaki on 16/11/03.
 */
public class DBActivity extends Activity {
    //LocalDatabaseService LOCAL_DATABASE_SERVICE = new LocalDatabaseService(new DatabaseHelper(this));
    LocalDatabaseService LOCAL_DATABASE_SERVICE = new LocalDatabaseService(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.show_localdb);
        ListView listView = (ListView)findViewById(R.id.listView2);

        List<String> allData = LOCAL_DATABASE_SERVICE.getAllData();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter.add("total " + Integer.toString(allData.size()) + " records.");
        if(!allData.isEmpty()) {
            for (String str : allData) {
                adapter.add(str);
            }
        }
        listView.setAdapter(adapter);
    }

    public void onClickResetDatabase(View view) {
        LOCAL_DATABASE_SERVICE.resetDatabase();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void onClickSaveDummy(View view) {
        LOCAL_DATABASE_SERVICE.saveDummyData();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void onClickSaveJSONArray(View view) {
        List<String> allData = new ArrayList<>();
        allData.add("{\"recipeId\":10,\"name\":\"肉じゃが\"}");
        allData.add("{\"recipeId\":12,\"name\":\"カルボナーラ\"}");
        allData.add("{\"recipeId\":21,\"name\":\"クリームスープパスタ\"}");
        allData.add("{\"recipeId\":20,\"name\":\"トマトスープパスタ\"}");
        JSONArray array = new JSONArray();
        for(String data : allData) {
            JSONObject obj;
            try {
                obj = new JSONObject(data);
                array.put(obj);
            } catch (JSONException e) {
                obj = new JSONObject();
            }
        }
        LOCAL_DATABASE_SERVICE.saveAllDateFromJSON(array, "2016-11-09");
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void onClickGetRecipeIdByDate(View view) {
        String date = "2016-11-30";
        int id = LOCAL_DATABASE_SERVICE.getRecipeIdByDate(date);
        ListView listView = (ListView)findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter.add("recipe in " + date);
        adapter.add(Integer.toString(id));
        listView.setAdapter(adapter);
    }

    public void onClickGetRecipeDateById(View view) {
        int id = 67;
        String date = LOCAL_DATABASE_SERVICE.getCookDateById(id);
        ListView listView = (ListView)findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter.add("recipe in " + date);
        adapter.add(Integer.toString(id));
        listView.setAdapter(adapter);
    }

    public void onClickGetBetween(View view) {
        setContentView(R.layout.show_localdb);
        ListView listView = (ListView)findViewById(R.id.listView2);

        Cursor c = LOCAL_DATABASE_SERVICE.getRecipeBetween("2016-11-09","2016-11-22");
        c.moveToFirst();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter.add("total " + Integer.toString(c.getCount()) + " records.");

        for(int i = 0; i < c.getCount(); i++){
            String str =
                    "[ID : " + c.getString(c.getColumnIndex(DatabaseHelper.RECIPE_ID)) + ","
                    + "Name : " + c.getString(c.getColumnIndex(DatabaseHelper.RECIPE_NAME)) + ","
                    + "Date : " + c.getString(c.getColumnIndex(DatabaseHelper.COOK_DATE)) + ","
                    + "Evaluation : " + c.getString(c.getColumnIndex(DatabaseHelper.EVALUATION))
                    + "]";

            adapter.add(str);
            c.moveToNext();
        }

        listView.setAdapter(adapter);
    }

    public void onClickGetBetweenReputation(View view) {
        setContentView(R.layout.show_localdb);
        ListView listView = (ListView)findViewById(R.id.listView2);

        String request = LOCAL_DATABASE_SERVICE.getRequestRecipeIds("2016-11-12", 14);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter.add("requests");
        adapter.add(request);

        listView.setAdapter(adapter);
    }

    public void onClickUpdateData(View view) {
        LOCAL_DATABASE_SERVICE.updateData(89,"Strawberry","2016-11-22",100);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void onClickSaveEvaluation(View view) {
        LOCAL_DATABASE_SERVICE.saveEvaluation("2016-11-09", 5);
        LOCAL_DATABASE_SERVICE.saveEvaluation("2016-11-10", -1);
        LOCAL_DATABASE_SERVICE.saveEvaluation("2016-11-11", 2);
        LOCAL_DATABASE_SERVICE.saveEvaluation("2016-11-12", 0);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
