package ch.hsr.mge.learnit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static ch.hsr.mge.learnit.database.DBHelper.DATABASE_NAME;

/**
 * Created by nico on 21/10/16.
 */

public class CardHelper extends DBHelper{

    public static final String CARD_TABLE_NAME = "card";
    public static final String CARD_COLUMN_ID = "cardid";
    public static final String CARD_COLUMN_FRONT = "front";
    public static final String CARD_COLUMN_BACK = "back";
    public static final String CARD_COLUMN_CARDSETNAME= "cardset";



    public CardHelper(Context context) {
        super(context);
    }
    //TODO: write create table query
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);

    }



    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

}
