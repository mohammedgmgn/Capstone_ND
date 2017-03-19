package com.mahmoud.mohammed.capstone_nd.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by siko on 3/19/2017.
 */

public class BooksDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "readshow.db";
    private static final int DATABASE_VERSION = 1;
    public static final String ITEMS = "items";

    public BooksDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
db.execSQL("CREATE TABLE"+ITEMS +" ("
        + BookContract.BookColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + BookContract.BookColumns.SERVER_ID + " TEXT,"
        + BookContract.BookColumns.TITLE + " TEXT NOT NULL,"
        + BookContract.BookColumns.DESCRIPTION + " TEXT NOT NULL,"
        + BookContract.BookColumns.PHOTO_URL + " TEXT NOT NULL,"
        + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ITEMS);
        onCreate(db);

    }
}
