package com.apptest.notepaperlab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    public static final String KEY_ID = "_id";
    public static final String KEY_NOTEDATE = "notedate";
    public static final String KEY_NOTETIME = "notetime";
    public static final String KEY_NOTECONTENTS = "notecontents";
    public static final String KEY_COLOR = "color";
    private static final String DATABASE_NAME = "NotePaperDB";
    private static final String TABLE_NAME = "notepapertb";
    private static final int DATABASE_VERSION = 4;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                KEY_ID + " integer PRIMARY KEY autoincrement," +
                KEY_NOTEDATE + " DATE NOT NULL," +
                KEY_NOTETIME + " DATETIME NOT NULL," +
                KEY_NOTECONTENTS + " TEXT NOT NULL," +
                KEY_COLOR + " NOT NULL);";

        Log.i("DB_CREATE",DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
