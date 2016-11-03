package jp.ac.titech.itpro.sdl.tsuyoso2.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mitsuaki on 16/11/03.
 * 使い方
 * Context を引数に初期化
 */
public class LocalDatabaseService {
    private DatabaseHelper mDbHelper;

    public LocalDatabaseService(Context ctx){
        this.mDbHelper = new DatabaseHelper(ctx);
    }


    /**
     * 下記のパラメータを指定すると新規に登録される
     * @param recipeId
     * @param recipeName
     * @param cookDate
     * @param evaluation
     */
    public void saveSingleData(int recipeId,
                               String recipeName,
                               String cookDate,
                               int evaluation
    ){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.RECIPE_ID, recipeId);
        values.put(DatabaseHelper.RECIPE_NAME, recipeName);
        String cookdate = cookDate.replaceAll("-","");
        values.put(DatabaseHelper.COOK_DATE, cookdate);
        values.put(DatabaseHelper.EVALUATION, evaluation);

        try {
            db.insert(DatabaseHelper.TABLE_NAME, null, values);
        } catch (Exception e) {

        } finally {
            db.close();
        }
    }

    /**
     * _id (primary key)を指定してレコードを削除する
     */
    public void deleteColumn(String id) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME, "_id = " + id, null);
    }

    /**
     * ローカルのデータベースの情報をすべて削除
     */
    public void resetDatabase() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String select_all = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + ";";
        Cursor c = db.rawQuery(select_all, null);
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            deleteColumn(c.getString(c.getColumnIndex(DatabaseHelper.ID)));
            c.moveToNext();
        }
    }

    /**
     * date(yyyy-mm-dd)を指定するとその日のメニューのid(int)が帰ってくる
     * @param date
     * @return if exists recipeid else -1
     */
    public int getRecipeIdByDate(String date) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String select_by_date =
                "SELECT * FROM " + DatabaseHelper.TABLE_NAME
                + " WHERE " + DatabaseHelper.COOK_DATE + " == " + date.replaceAll("-","") + ";";
        Cursor c = db.rawQuery(select_by_date, null);
        c.moveToFirst();
        if(c.getCount() == 0){
            return -1;
        } else {
            return c.getInt(c.getColumnIndex(DatabaseHelper.RECIPE_ID));
        }
    }

    /**
     * startDate と endDate　の間のデータ（Cursor形式）を取得する
     * @param startDate
     * @param endDate
     * @return
     */
    public Cursor getRecipeBetween(String startDate, String endDate) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String start = startDate.replaceAll("-","");
        String end = endDate.replaceAll("-","");
        String select_start_to_end =
                "SELECT * FROM " + DatabaseHelper.TABLE_NAME
                + " WHERE "
                        + DatabaseHelper.COOK_DATE + " >= " + start
                        + " AND "
                        + DatabaseHelper.COOK_DATE + " <= " + end
                + " ORDER BY " + DatabaseHelper.COOK_DATE + " DESC"
                +";";
        Cursor c = db.rawQuery(select_start_to_end, null);
        return c;
    }

    /**
     * サンプルで作ったダミーのデータ
     public void saveDummyData(){
     saveSingleData(12, "Curry", "2016-11-10", 1);
     saveSingleData(23, "Stew", "2016-11-11", 2);
     saveSingleData(34, "Hamburg", "2016-11-12", 3);
     saveSingleData(45, "Coffee", "2016-11-13", 4);
     saveSingleData(56, "Rice", "2016-11-14", 5);
     saveSingleData(67, "Cake", "2016-11-18", 5);
     saveSingleData(78, "Rice cake", "2016-11-09", 5);
     saveSingleData(89, "Strawberry", "2016-11-22", 5);
     saveSingleData(90, "Beer", "2016-10-14", 5);
     saveSingleData(91, "Tacos", "2016-11-16", 5);
    }
     */

    /**
     * @return

    public List<String> getAllData() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String sql_request =
                "SELECT * FROM " + DatabaseHelper.TABLE_NAME
                        + " ORDER BY " + DatabaseHelper.COOK_DATE + " DESC" + ";";
        List<String> allData = new ArrayList<>();

        Cursor c = db.rawQuery(sql_request, null);
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            String query = "";
            query += c.getString(c.getColumnIndex(DatabaseHelper.ID));
            query += " ";
            query += c.getString(c.getColumnIndex(DatabaseHelper.RECIPE_ID));
            query += " ";
            query += c.getString(c.getColumnIndex(DatabaseHelper.RECIPE_NAME));
            query += " ";
            query += c.getString(c.getColumnIndex(DatabaseHelper.COOK_DATE));
            allData.add(query);
            c.moveToNext();
        }
        if (allData.isEmpty()) {
            return Collections.emptyList();
        } else {
            return allData;
        }
    }
     */

}
