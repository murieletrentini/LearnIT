package ch.hsr.mge.learnit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ch.hsr.mge.learnit.domain.Card;
import ch.hsr.mge.learnit.domain.CardSet;
import ch.hsr.mge.learnit.domain.CardSets;

import static android.database.DatabaseUtils.dumpCursorToString;
import static ch.hsr.mge.learnit.database.CardHelper.CARD_COLUMN_BACK;
import static ch.hsr.mge.learnit.database.CardHelper.CARD_COLUMN_CARDSETID;
import static ch.hsr.mge.learnit.database.CardHelper.CARD_COLUMN_FRONT;
import static ch.hsr.mge.learnit.database.CardHelper.CARD_COLUMN_ID;
import static ch.hsr.mge.learnit.database.CardHelper.CARD_TABLE_NAME;
import static ch.hsr.mge.learnit.database.CardSetHelper.CARDSET_COLUMN_ID;
import static ch.hsr.mge.learnit.database.CardSetHelper.CARDSET_COLUMN_NAME;
import static ch.hsr.mge.learnit.database.CardSetHelper.CARDSET_TABLE_NAME;

//import static ch.hsr.mge.learnit.database.CardSetHelper.CARDSET_COLUMN_ID;

/**
 * Created by nico on 21/10/16.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";

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

    public boolean updateCardSet(String oldName, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARDSET_COLUMN_NAME, newName);
        db.update(CARDSET_TABLE_NAME, values, CARDSET_COLUMN_NAME + "=?", new String[] { oldName });
        return true;
    }

    public boolean updateCard(String[] oldStrings, String[] newStrings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARD_COLUMN_FRONT, newStrings[0]);
        db.update(CARD_TABLE_NAME, values, CARD_COLUMN_FRONT + "=?", new String[] { oldStrings[0] });
        values = new ContentValues();
        values.put(CARD_COLUMN_BACK, newStrings[1]);
        db.update(CARD_TABLE_NAME, values, CARD_COLUMN_BACK + "=?", new String[] { oldStrings[1] });
        return true;
    }

    public CardSet getCardSet(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                CARDSET_TABLE_NAME + " WHERE " + CARDSET_COLUMN_ID + "=?", new String[] { id });
        Log.d("1", dumpCursorToString(cursor));
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

    public void deleteCardSet(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CARDSET_TABLE_NAME, CARDSET_COLUMN_ID + " = ? ", new String[] { name });
    }

    public boolean insertCard(String front, String back, String cardSetID) {
        //SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cardSetCursor = db.rawQuery( "SELECT '" + CARDSET_COLUMN_ID + "' FROM " +
        //        CARDSET_TABLE_NAME + " WHERE '" + CARDSET_COLUMN_NAME +
        //        "' = ?", new String[] { cardSetName });
        SQLiteDatabase db = this.getWritableDatabase();
        //cardSetCursor.moveToFirst();
        //cardSetId = cardSetCursor.getInt(cardSetCursor.getColumnIndex(CardSetHelper.CARDSET_COLUMN_ID));
        ContentValues values =  new ContentValues();
        //values.put(CARDSET_COLUMN_ID, ++cardIDCounter);
        values.put(CARD_COLUMN_FRONT, front);
        values.put(CARD_COLUMN_BACK, back);
        values.put(CARD_COLUMN_CARDSETID, cardSetID);
        db.insert(CARD_TABLE_NAME, null, values);
        //cardSetCursor.close();
        return true;
    }

    public void deleteCard(Integer cardID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CARD_TABLE_NAME, CARD_COLUMN_ID + " = ?", new String[] { Integer.toString(cardID) });
    }
    //select count(id) from  *
    public int getAmountOfCards() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor numberCursor = db.rawQuery( "SELECT count(" + CARD_COLUMN_ID + ") as amount FROM " + CARD_TABLE_NAME, null);
        Integer amountOfCards = numberCursor.getInt(numberCursor.getColumnIndex("amount"));
        numberCursor.close();
        return amountOfCards;
    }

    public void deleteContent() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + CARD_TABLE_NAME + " ;");
        db.execSQL("DELETE FROM " + CARDSET_TABLE_NAME + " ;");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}
