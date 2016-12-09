package jp.ac.titech.itpro.sdl.tsuyoso2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mitsuaki on 16/10/12.
 */

/**
 * データベースの初期化やテーブル定義などを行う
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "tsuyosodb";
    private static final int DBVERSION = 1;
    private static final String TABLE_NAME = "tbluser_log";
    /**
     * cook_date is text describe YYYYMMDD
     */
    private static final String CREATE_LOCAL_DATA_SQL =
            "create table " + TABLE_NAME
                    + "("
                    + "_id integer  primary key autoincrement,"
                    + "recipe_id    integer not null,"
                    + "recipe_name  text not null,"
                    + "cook_date    text not null,"
                    + "evaluation   integer,"
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