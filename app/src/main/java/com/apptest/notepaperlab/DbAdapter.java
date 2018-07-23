package com.apptest.notepaperlab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DbAdapter {
    public static final String KEY_ID = "_id";
    public static final String KEY_NOTEDATE = "notedate";
    public static final String KEY_NOTETIME = "notetime";
    public static final String KEY_NOTECONTENTS = "notecontents";
    public static final String KEY_COLOR = "color";
    private static final String TABLE_NAME = "notepapertb";
    private DbHelper mDbHelper;
    private SQLiteDatabase mdb;
    private final Context mCtx;
    private ContentValues values;

    public DbAdapter(Context mCtx) {
        this.mCtx = mCtx;
        open();
    }

    public void open() {
        mDbHelper = new DbHelper(mCtx);
        mdb = mDbHelper.getWritableDatabase();
        Log.i("DB_open",mdb.toString());
    }

    public void close(){
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }
    public Cursor listContacts(){
        Cursor mCursor = mdb.query(TABLE_NAME, new String[] {KEY_ID, KEY_NOTEDATE, KEY_NOTETIME, KEY_NOTECONTENTS, KEY_COLOR},null,null,null,null,null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public long createContacts(String notedate, String notetime, String notecontents, String clolor) {
        long rowsAffected = -1;
        try{
            values = new ContentValues();
            values.put(KEY_NOTEDATE, notedate);
            values.put(KEY_NOTETIME, notetime);
            values.put(KEY_NOTECONTENTS, notecontents);
            values.put(KEY_COLOR, clolor);
            rowsAffected = mdb.insert(TABLE_NAME,null,values);
//            rowsAffected = mdb.insertOrThrow(TABLE_NAME,null,values);
            Log.i("DB_Insert_rowsAffected", Long.toString(rowsAffected));
            return rowsAffected;
//        } catch (SQLException e) {
        } catch (Exception e) {
            Log.i("DB_新增失敗", e.toString());
            Toast.makeText(mCtx,"新增失敗!", Toast.LENGTH_SHORT).show();
            return rowsAffected;
          }
        finally {
            if (rowsAffected == -1)
                Toast.makeText(mCtx,"新增失敗!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mCtx,"新增成功!", Toast.LENGTH_SHORT).show();
        }
    }

    public long updateContacts(String id, String notedate, String notetime, String notecontents, String clolor){
        long rowsAffected = 0;
        try{
             values = new ContentValues();
             values.put(KEY_NOTEDATE, notedate);
             values.put(KEY_NOTETIME, notetime);
             values.put(KEY_NOTECONTENTS, notecontents);
             values.put(KEY_COLOR, clolor);
             rowsAffected = mdb.update(TABLE_NAME, values, "_id=" + id,null);
             Log.i("DB_Update_rowsAffected", Long.toString(rowsAffected));
//             long rowsAffected = mdb.update(TABLE_NAME, values, "_id=9999",null);
             return rowsAffected;
        }catch(SQLException e){
              Log.i("DB_修改失敗", e.toString());
              Toast.makeText(mCtx,"修改失敗!", Toast.LENGTH_SHORT).show();
              return -1;
        }finally {
            if (rowsAffected == 1)
                Toast.makeText(mCtx,"修改成功!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mCtx,"修改失敗!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean deleteContacts(String id){
        String[] args = {id};
  //      String[] args = {Integer.toString(999)};
        long rowsAffected = 0;
        rowsAffected = mdb.delete(TABLE_NAME, "_id= ?",args);
        Log.i("DB_Delete_rowsAffected", Long.toString(rowsAffected));
        if (rowsAffected == 1)
            Toast.makeText(mCtx,"刪除成功!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(mCtx,"刪除失敗!", Toast.LENGTH_SHORT).show();
        return true;
    }

    public Cursor queryById(String item_id){
        Cursor mCursor = null;
//        Log.i("DB_DbAdapter_queryByName",item_id);
        mCursor = mdb.query(true, TABLE_NAME, new String[] {KEY_ID, KEY_NOTEDATE, KEY_NOTETIME, KEY_NOTECONTENTS, KEY_COLOR},
                KEY_ID + " = " +item_id, null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public boolean dropTableMember(String id){
        mdb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.i("DB_DROP",TABLE_NAME);
//        Toast.makeText(mCtx,TABLE_NAME+"刪除成功!", Toast.LENGTH_SHORT).show();

        final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                KEY_ID + " integer PRIMARY KEY autoincrement," +
                KEY_NOTEDATE + " DATE NOT NULL," +
                KEY_NOTETIME + " DATETIME NOT NULL," +
                KEY_NOTECONTENTS + " TEXT NOT NULL," +
                KEY_COLOR + " INT NOT NULL);";

        mdb.execSQL(DATABASE_CREATE);
        Log.i("DB_CREATE",TABLE_NAME);
        return true;
    }

}
