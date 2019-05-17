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
        values.put("name", "AG2R La Mondiale");
        values.put("country", "France");
        db.insert("teams", null, values);

        values.put("id", 2);
        values.put("name", "Astana Pro Team");
        values.put("country", "Kazakhstan");
        db.insert("teams", null, values);

        values.put("id", 3);
        values.put("name", "Bahrain Merida");
        values.put("country", "Bahrain");
        db.insert("teams", null, values);

        values.put("id", 4);
        values.put("name", "BORA - hansgrohe");
        values.put("country", "Germany");
        db.insert("teams", null, values);

        values.put("id", 5);
        values.put("name", "CCC Team");
        values.put("country", "Poland");
        db.insert("teams", null, values);

        values.put("id", 6);
        values.put("name", "Deceuninck - Quick Step");
        values.put("country", "Belgium");
        db.insert("teams", null, values);

        values.put("id", 7);
        values.put("name", "EF Education First");
        values.put("country", "United States");
        db.insert("teams", null, values);

        values.put("id", 8);
        values.put("name", "Groupama - FDJ");
        values.put("country", "France");
        db.insert("teams", null, values);

        values.put("id", 9);
        values.put("name", "Lotto Soudal");
        values.put("country", "Belgium");
        db.insert("teams", null, values);

        values.put("id", 10);
        values.put("name", "Mitchelton-Scott");
        values.put("country", "Australia");
        db.insert("teams", null, values);

        values.put("id", 11);
        values.put("name", "Movistar Team");
        values.put("country", "Spain");
        db.insert("teams", null, values);

        values.put("id", 12);
        values.put("name", "Team Dimension Data");
        values.put("country", "South Africa");
        db.insert("teams", null, values);

        values.put("id", 13);
        values.put("name", "Team INEOS");
        values.put("country", "Great Britain");
        db.insert("teams", null, values);

        values.put("id", 14);
        values.put("name", "Team Jumbo-Visma");
        values.put("country", "Netherlands");
        db.insert("teams", null, values);

        values.put("id", 15);
        values.put("name", "Team Katusha - Alpecin");
        values.put("country", "Switzerland");
        db.insert("teams", null, values);

        values.put("id", 16);
        values.put("name", "Team Sunweb");
        values.put("country", "Germany");
        db.insert("teams", null, values);

        values.put("id", 17);
        values.put("name", "Trek - Segafredo");
        values.put("country", "United States");
        db.insert("teams", null, values);

        values.put("id", 18);
        values.put("name", "UAE-Team Emirates");
        values.put("country", "United Arab Emirates");
        db.insert("teams", null, values);
    }

    /**
     * Zatvori spojenie s DB
     */
    public void close() {
        db.close();
    }
}
