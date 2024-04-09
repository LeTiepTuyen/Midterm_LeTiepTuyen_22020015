package com.midterm.letieptuyen;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper  extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "questions.db";
    private static final String TABLE_NAME = "questions_table";
    private static final String COL_QUESTION = "question";
    private static final String COL_ANSWER = "answer";

    public DatabaseHelper(Context context, String databasePath) {
        super(context, databasePath, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_QUESTION + " TEXT, " +
                COL_ANSWER + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean addQuestion(String question, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_QUESTION, question);
        contentValues.put(COL_ANSWER, answer);

        long result = db.insert(TABLE_NAME, null, contentValues);

        // Return true if data is inserted correctly, otherwise false
        return result != -1;
    }
}
