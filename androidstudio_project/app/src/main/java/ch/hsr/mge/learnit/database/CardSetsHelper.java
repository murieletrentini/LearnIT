package ch.hsr.mge.learnit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nico on 21/10/16.
 */

public class CardSetHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "cardset"

    CardSetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //TODO: write create table query
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

}
