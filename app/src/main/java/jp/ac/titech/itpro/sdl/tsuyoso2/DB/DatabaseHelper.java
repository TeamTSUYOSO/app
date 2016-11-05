package jp.ac.titech.itpro.sdl.tsuyoso2.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mitsuaki on 16/11/03.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "tsuyosoapp.db";
    public static final int DBVERSION = 1;
    public static final String TABLE_NAME = "tbluser_log";
    public static final String ID = "_id";
    public static final String RECIPE_ID = "recipe_id";
    public static final String RECIPE_NAME = "recipe_name";
    public static final String COOK_DATE = "cook_date";
    public static final String EVALUATION = "evaluation";

    /**
     * cook_date is text describe YYYY/MM/DD
     */
    private static final String CREATE_LOCAL_DATA_SQL =
            "create table " + TABLE_NAME
                    + "("
                    + ID          + " integer primary key autoincrement,"
                    + RECIPE_ID   + " integer not null,"
                    + RECIPE_NAME + " text not null,"
                    + COOK_DATE   + " text unique not null,"
                    + EVALUATION  + " integer"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //sql code to create new table
        db.execSQL(CREATE_LOCAL_DATA_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //リリース後にデータベースを変更したいときにここにalter tableみたいに記述する
    }
}