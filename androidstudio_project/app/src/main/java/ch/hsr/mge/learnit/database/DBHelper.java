package ch.hsr.mge.learnit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.mge.learnit.domain.Card;
import ch.hsr.mge.learnit.domain.CardSet;
import ch.hsr.mge.learnit.domain.CardSets;

import static ch.hsr.mge.learnit.database.CardHelper.CARD_COLUMN_BACK;
import static ch.hsr.mge.learnit.database.CardHelper.CARD_COLUMN_CARDSETNAME;
import static ch.hsr.mge.learnit.database.CardHelper.CARD_COLUMN_FRONT;
import static ch.hsr.mge.learnit.database.CardHelper.CARD_COLUMN_ID;
import static ch.hsr.mge.learnit.database.CardHelper.CARD_TABLE_NAME;
//import static ch.hsr.mge.learnit.database.CardSetHelper.CARDSET_COLUMN_ID;
import static ch.hsr.mge.learnit.database.CardSetHelper.CARDSET_COLUMN_NAME;
import static ch.hsr.mge.learnit.database.CardSetHelper.CARDSET_TABLE_NAME;

/**
 * Created by nico on 21/10/16.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";
    private int cardIDCounter = 0;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CARD_TABLE_NAME + "(" +
                CARD_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CARD_COLUMN_FRONT + " TEXT, " +
                CARD_COLUMN_BACK + " TEXT, " +
                CARD_COLUMN_CARDSETNAME + " INTEGER, " +
                "FOREIGN KEY(" + CARD_COLUMN_CARDSETNAME+ ") REFERENCES " + CARDSET_TABLE_NAME + "(" + CARD_COLUMN_CARDSETNAME + "));"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CARDSET_TABLE_NAME + "(" +
                CARDSET_COLUMN_NAME + " TEXT PRIMARY KEY);"
        );
    }
    public boolean insertCardSet(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put(CARDSET_COLUMN_NAME, name);
        db.insert(CARDSET_TABLE_NAME, null, values);
        return true;
    }

    public boolean updateCardSet(String oldName, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARDSET_COLUMN_NAME, newName);
        db.update(CARDSET_TABLE_NAME, values, CARDSET_COLUMN_NAME + " ? ", new String[] { oldName });
        return true;
    }

    public List<Card> getCardSet(String name) {
        List<Card> cardSet = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + CARDSET_TABLE_NAME + " WHERE " +
                CARDSET_COLUMN_NAME + "=?", new String[] { name });
        while (cursor.moveToFirst()) {
                Card card = new Card();
                card.setFront(cursor.getString(1));
                card.setBack(cursor.getString(2));
                cardSet.add(card);
            }
        cursor.close();
            return cardSet;
        }


    public CardSets getAllCardSets() {
        List<CardSet> cardSetsList = new ArrayList<>();
        CardSets cardSets;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT " + CARDSET_COLUMN_NAME + " FROM " + CARDSET_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                CardSet cardSet = new CardSet();
                cardSet.setTitle(cursor.getString(0));
                cardSetsList.add(cardSet);
                cursor.moveToNext();
            }
        }
        cardSets = new CardSets(cardSetsList);
        cursor.close();
        return cardSets;
    }

    public void deleteCardSet(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CARDSET_TABLE_NAME, CARDSET_COLUMN_NAME + " = ? ", new String[] { name });
    }

    public boolean insertCard(String front, String back, String cardSetName) {
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
        values.put(CARD_COLUMN_CARDSETNAME, cardSetName);
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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}
