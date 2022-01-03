package com.example.android_werkstuk_nabil_lahssini;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.android_werkstuk_nabil_lahssini.Entities.DbMovie;
import com.example.android_werkstuk_nabil_lahssini.Entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "my_db.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "movies";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_OVERVIEW = "overview";
    private static final String COLUMN_RUNTIME = "runtime";
    private static final String COLUMN_RELEASEDATE = "release_date";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_OVERVIEW + " TEXT, " +
                COLUMN_RUNTIME + " INTEGER," +
                COLUMN_RELEASEDATE + " TEXT);";
        db.execSQL(query);
    }
    void addMovie(int id, String title, String overview, int runtime, String release_date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, id);
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_OVERVIEW, overview);
        cv.put(COLUMN_RUNTIME, runtime);
        cv.put(COLUMN_RELEASEDATE, release_date);
        long result;
        try{
            result = db.insertOrThrow(TABLE_NAME,null, cv);
        } catch (SQLiteConstraintException e){
            result = -1;
        }
        if (result == -1) {
            Toast.makeText(context, context.getString(R.string.already_added), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getString(R.string.succes_message), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public ArrayList<DbMovie> readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        ArrayList<DbMovie> movies = new ArrayList<DbMovie>();
        if (cursor.moveToFirst()) {
            do {
                movies.add(new DbMovie(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        return movies;
    }
    public void deleteOneRow(String row_id) {
        System.out.println(row_id);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, context.getString(R.string.filaed_message), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getString(R.string.succes_message), Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
}
