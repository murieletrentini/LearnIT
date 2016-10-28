package ch.hsr.mge.learnit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ch.hsr.mge.learnit.domain.Card;
import ch.hsr.mge.learnit.domain.CardSet;
import ch.hsr.mge.learnit.domain.CardSets;

public class DBHelper extends SQLiteOpenHelper {

    //constants for Database names and rows
    public static final String CARD_TABLE_NAME = "card";
    public static final String CARD_COLUMN_ID = "cardid";
    public static final String CARD_COLUMN_FRONT = "front";
    public static final String CARD_COLUMN_BACK = "back";
    public static final String CARD_COLUMN_CARDSETID= "cardsetid";
    public static final String CARDSET_TABLE_NAME = "cardset";
    public static final String CARDSET_COLUMN_ID= "cardsetid";
    public static final String CARDSET_COLUMN_NAME = "cardsetname";
    public static final String DATABASE_NAME = "SQLiteDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CARD_TABLE_NAME + "(" +
                CARD_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CARD_COLUMN_FRONT + " TEXT, " +
                CARD_COLUMN_BACK + " TEXT, " +
                CARD_COLUMN_CARDSETID + " TEXT, " +
                "FOREIGN KEY(" + CARD_COLUMN_CARDSETID + ") REFERENCES " + CARDSET_TABLE_NAME + "(" + CARDSET_COLUMN_ID + "));"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CARDSET_TABLE_NAME + "(" +
                CARDSET_COLUMN_ID + " TEXT PRIMARY KEY, " +
                CARDSET_COLUMN_NAME + " TEXT);"
        );
    }
    public boolean insertCardSet(String name, String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put(CARDSET_COLUMN_ID, uid);
        values.put(CARDSET_COLUMN_NAME, name);
        db.insert(CARDSET_TABLE_NAME, null, values);
        return true;
    }

    public CardSet getCardSet(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                CARDSET_TABLE_NAME + " WHERE " + CARDSET_COLUMN_ID + "=?", new String[] { id });
        cursor.moveToFirst();
        CardSet set = new CardSet(cursor.getString(1));
        cursor = db.rawQuery( "SELECT * FROM " + CARD_TABLE_NAME+ " WHERE " +
                CARD_COLUMN_CARDSETID + "=?", new String[] { id });
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Card card = new Card();
                card.setFront(cursor.getString(1));
                card.setBack(cursor.getString(2));
                set.addCard(card);
                cursor.moveToNext();
                }
            }
        cursor.close();
        return set;
        }

    public CardSets getAllCardSets() {
        CardSets cardSets = new CardSets();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + CARDSET_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(0);
                CardSet cardSet = getCardSet(id);
                cardSets.addCardSet(cardSet);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return cardSets;
    }

    public boolean insertCard(String front, String back, String cardSetID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CARD_COLUMN_FRONT, front);
        values.put(CARD_COLUMN_BACK, back);
        values.put(CARD_COLUMN_CARDSETID, cardSetID);
        db.insert(CARD_TABLE_NAME, null, values);
        return true;
    }

    public void deleteContent() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + CARD_TABLE_NAME + " ;");
        db.execSQL("DELETE FROM " + CARDSET_TABLE_NAME + " ;");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}
