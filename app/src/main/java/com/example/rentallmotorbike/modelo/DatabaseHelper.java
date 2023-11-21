package com.example.rentallmotorbike.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "favorite_items.db";
    public static final String TABLE_NAME = "favorite_items_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ITEM_NAME";
    public static final String COL_3 = "ITEM_PRICE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ITEM_NAME TEXT,ITEM_PRICE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String itemName, String itemPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, itemName);
        contentValues.put(COL_3, itemPrice);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +" where item_name= "+id+"", null);
        return res;
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete(TABLE_NAME, whereClause, whereArgs);
    }
}
