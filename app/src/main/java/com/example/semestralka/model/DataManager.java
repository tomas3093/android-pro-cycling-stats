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


    /**
     * Vrati tim so zadanym ID
     * @param teamId
     * @return
     */
    public Team getTeam(int teamId) {
        Cursor c = db.rawQuery("SELECT id, name, country FROM teams WHERE id = ?", new String[] {teamId + ""});
        c.moveToFirst();
        if (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String country = c.getString(c.getColumnIndex("country"));

            return new Team(id, name, country);
        }
        c.close();

        return null;
    }


    /**
     * Vrati cyklistu so zadanym ID
     * @param cyclistId
     * @return
     */
    public Cyclist getCyclist(int cyclistId) {
        Cursor c = db.rawQuery("SELECT id, name, surname, birthDate, teamId, nationality, weight, height FROM cyclists WHERE id = ?", new String[] {cyclistId + ""});
        c.moveToFirst();
        if (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String surname = c.getString(c.getColumnIndex("surname"));
            String birthDate = c.getString(c.getColumnIndex("birthDate"));
            Team team = getTeam(c.getInt(c.getColumnIndex("teamId")));
            String nationality = c.getString(c.getColumnIndex("nationality"));
            int weight = c.getInt(c.getColumnIndex("weight"));
            int height = c.getInt(c.getColumnIndex("height"));

            return new Cyclist(id, name, surname, birthDate, team, nationality, weight, height);
        }
        c.close();

        return null;
    }


    /**
     * Vrati vsetkych existujucich cyklistov
     * @return
     */
    public List<Cyclist> getAllCyclists() {
        ArrayList<Cyclist> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT id, name, surname, birthDate, teamId, nationality, weight, height FROM cyclists", null);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String surname = c.getString(c.getColumnIndex("surname"));
            String birthDate = c.getString(c.getColumnIndex("birthDate"));
            Team team = getTeam(c.getInt(c.getColumnIndex("teamId")));
            String nationality = c.getString(c.getColumnIndex("nationality"));
            int weight = c.getInt(c.getColumnIndex("weight"));
            int height = c.getInt(c.getColumnIndex("height"));

            list.add(new Cyclist(id, name, surname, birthDate, team, nationality, weight, height));
            c.moveToNext();
        }
        c.close();

        return list;
    }


    /**
     * Vrati vsetkych cyklistov zo zadaneho timu
     * @param teamId
     * @return
     */
    public List<Cyclist> getAllCyclists(int teamId) {
        ArrayList<Cyclist> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT id, name, surname, birthDate, teamId, nationality, weight, height FROM cyclists WHERE teamId = ?", new String[] {String.valueOf(teamId)});

        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String surname = c.getString(c.getColumnIndex("surname"));
            String birthDate = c.getString(c.getColumnIndex("birthDate"));
            Team team = getTeam(c.getInt(c.getColumnIndex("teamId")));
            String nationality = c.getString(c.getColumnIndex("nationality"));
            int weight = c.getInt(c.getColumnIndex("weight"));
            int height = c.getInt(c.getColumnIndex("height"));

            list.add(new Cyclist(id, name, surname, birthDate, team, nationality, weight, height));
            c.moveToNext();
        }
        c.close();

        return list;
    }


    /**
     * Vrati vsetky timy z DB
     * @return
     */
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


    /**
     * Prida novy tim do DB
     * @param team
     */
    public void addTeam(Team team) {
        // TODO
    }


    /**
     * Prida noveho cyklistu
     * @param cyclist
     */
    public void addCyclist(Cyclist cyclist) {
        // TODO
    }


    /**
     * Vlozi predpripravene data do databazy
     */
    public void loadExampleData() {

        ContentValues values = new ContentValues();

        // TEAMS
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


        // CYCLISTS
        values.clear();
        values.put("id", 1);
        values.put("name", "Peter");
        values.put("surname", "Sagan");
        values.put("birthDate", "1990-01-26");
        values.put("teamId", 4);
        values.put("nationality", "Slovakia");
        values.put("weight", 73);
        values.put("height", 184);
        db.insert("cyclists", null, values);

        values.put("id", 2);
        values.put("name", "Daniel");
        values.put("surname", "Oss");
        values.put("birthDate", "1987-01-13");
        values.put("teamId", 4);
        values.put("nationality", "Italy");
        values.put("weight", 75);
        values.put("height", 190);
        db.insert("cyclists", null, values);

        values.put("id", 3);
        values.put("name", "Chris");
        values.put("surname", "Froome");
        values.put("birthDate", "1985-05-20");
        values.put("teamId", 13);
        values.put("nationality", "Great Britain");
        values.put("weight", 69);
        values.put("height", 186);
        db.insert("cyclists", null, values);

        values.put("id", 4);
        values.put("name", "Primož");
        values.put("surname", "Roglič");
        values.put("birthDate", "1989-10-29");
        values.put("teamId", 14);
        values.put("nationality", "Slovenia");
        values.put("weight", 65);
        values.put("height", 177);
        db.insert("cyclists", null, values);

        values.put("id", 5);
        values.put("name", "Wout");
        values.put("surname", "van Aert");
        values.put("birthDate", "1994-9-15");
        values.put("teamId", 14);
        values.put("nationality", "Belgium");
        values.put("weight", 78);
        values.put("height", 187);
        db.insert("cyclists", null, values);

        values.put("id", 6);
        values.put("name", "Steven");
        values.put("surname", "Kruijswijk");
        values.put("birthDate", "1987-6-7");
        values.put("teamId", 14);
        values.put("nationality", "Netherlands");
        values.put("weight", 66);
        values.put("height", 178);
        db.insert("cyclists", null, values);

        values.put("id", 7);
        values.put("name", "Tony");
        values.put("surname", "Martin");
        values.put("birthDate", "1985-4-23");
        values.put("teamId", 14);
        values.put("nationality", "Germany");
        values.put("weight", 75);
        values.put("height", 185);
        db.insert("cyclists", null, values);

        values.put("id", 8);
        values.put("name", "Dylan");
        values.put("surname", "Groenewegen");
        values.put("birthDate", "1993-6-21");
        values.put("teamId", 14);
        values.put("nationality", "Netherlands");
        values.put("weight", 70);
        values.put("height", 177);
        db.insert("cyclists", null, values);
    }

    /**
     * Zatvori spojenie s DB
     */
    public void close() {
        db.close();
    }
}
