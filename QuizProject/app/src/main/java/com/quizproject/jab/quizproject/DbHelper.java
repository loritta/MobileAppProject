package com.quizproject.jab.quizproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "QuizDb";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SchemaContract.Results.TABLE_NAME + " (" +
                    SchemaContract.Results._ID + " INTEGER PRIMARY KEY," +
                    SchemaContract.Results.COLUMN_NAME_USER_EMAIL + " TEXT," +
                    SchemaContract.Results.COLUMN_NAME_QUIZ_QUESTIONS + " TEXT," +
                    SchemaContract.Results.COLUMN_NAME_QUIZ_RESULTS + " TEXT)";

    // same for the delete query
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SchemaContract.Results.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        db.rawQuery("DROP TABLE IF EXISTS results", null, null);
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // the data is really not sensitive
        db.execSQL(SQL_DELETE_ENTRIES);

        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}