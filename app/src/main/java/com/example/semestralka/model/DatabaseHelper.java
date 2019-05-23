package com.example.semestralka.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Trieda zabezpecuje pripojenie a inicializaciu databazy SQLite, vytvorenie tabuliek
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cycling_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLES
        db.execSQL("CREATE TABLE IF NOT EXISTS teams (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "country TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS cyclists (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "surname TEXT, " +
                "birthYear INTEGER, " +
                "teamId INTEGER, " +
                "nationality TEXT, " +
                "weight INTEGER, " +
                "height INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS teams;");
        db.execSQL("DROP TABLE IF EXISTS cyclists;");
        onCreate(db);
    }


}
