package com.example.semestralka.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Trieda zabezpecuje zakladne operacie s datami v databaze
 * je implementovana ako Singleton, teda drzi jednu instanciu dat
 */
public class DataManager {

    private static DataManager instance;

    public static DataManager getInstance(Context context) {
        if (instance == null)
            instance = new DataManager(context);

        return instance;
    }

    private SQLiteDatabase db;

    private DataManager(Context context) {
        db = new DatabaseHelper(context).getWritableDatabase();
    }

    public List<Team> getAllTeams() {
        ArrayList<Team> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT id, name, country FROM teams", null);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String country = c.getString(c.getColumnIndex("country"));

            list.add(new Team(id, name, country));
            c.moveToNext();
        }
        c.close();

        return list;
    }

    public void upsertTeam(Team team) {
        // TODO
    }

    public void loadExampleData() {

        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("name", "Astana Pro Team");
        values.put("country", "Kazakhstan");
        db.insert("teams", null, values);

        values.put("id", 2);
        values.put("name", "Bahrain Merida");
        values.put("country", "Bahrain");
        db.insert("teams", null, values);

        values.put("id", 3);
        values.put("name", "Deceuninck - Quick Step");
        values.put("country", "Belgium");
        db.insert("teams", null, values);
    }

    /**
     * Zatvori spojenie s DB
     */
    public void close() {
        db.close();
    }
}
