package com.example.semestralka.model;

import android.annotation.SuppressLint;
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
     * Vrati tim so zadanym nazvom
     * @param teamName
     * @return
     */
    public Team getTeam(String teamName) {
        Cursor c = db.rawQuery("SELECT id, name, country FROM teams WHERE name = ?", new String[] {teamName});
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
        Cursor c = db.rawQuery("SELECT id, name, surname, birthYear, teamId, nationality, weight, height FROM cyclists WHERE id = ?", new String[] {cyclistId + ""});
        c.moveToFirst();
        if (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String surname = c.getString(c.getColumnIndex("surname"));
            int birthYear = c.getInt(c.getColumnIndex("birthYear"));
            Team team = getTeam(c.getInt(c.getColumnIndex("teamId")));
            String nationality = c.getString(c.getColumnIndex("nationality"));
            int weight = c.getInt(c.getColumnIndex("weight"));
            int height = c.getInt(c.getColumnIndex("height"));

            return new Cyclist(id, name, surname, birthYear, team, nationality, weight, height);
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
        Cursor c = db.rawQuery("SELECT id, name, surname, birthYear, teamId, nationality, weight, height FROM cyclists", null);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String surname = c.getString(c.getColumnIndex("surname"));
            int birthYear = c.getInt(c.getColumnIndex("birthYear"));
            Team team = getTeam(c.getInt(c.getColumnIndex("teamId")));
            String nationality = c.getString(c.getColumnIndex("nationality"));
            int weight = c.getInt(c.getColumnIndex("weight"));
            int height = c.getInt(c.getColumnIndex("height"));

            list.add(new Cyclist(id, name, surname, birthYear, team, nationality, weight, height));
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
        Cursor c = db.rawQuery("SELECT id, name, surname, birthYear, teamId, nationality, weight, height FROM cyclists WHERE teamId = ?", new String[] {String.valueOf(teamId)});

        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String surname = c.getString(c.getColumnIndex("surname"));
            int birthYear = c.getInt(c.getColumnIndex("birthYear"));
            Team team = getTeam(c.getInt(c.getColumnIndex("teamId")));
            String nationality = c.getString(c.getColumnIndex("nationality"));
            int weight = c.getInt(c.getColumnIndex("weight"));
            int height = c.getInt(c.getColumnIndex("height"));

            list.add(new Cyclist(id, name, surname, birthYear, team, nationality, weight, height));
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
     * Vrati nazvy vsetkych timov z DB
     * @return
     */
    public String[] getAllTeamNames() {
        ArrayList<String> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT name FROM teams", null);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            String name = c.getString(c.getColumnIndex("name"));
            list.add(name);
            c.moveToNext();
        }
        c.close();

        String[] arrToReturn = new String[list.size()];
        arrToReturn = list.toArray(arrToReturn);

        return arrToReturn;
    }


    /**
     * Prida novy tim do DB
     * @param team
     */
    public void addTeam(Team team) {

        // Ziskanie ID
        int teamId = 0;
        Cursor c = db.rawQuery("SELECT MAX(id) AS _id FROM teams", null);
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                teamId = c.getInt(c.getColumnIndex("_id")) + 1;
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            c.close();
        }

        // Vlozenie timu do DB
        if (teamId > 0) {
            try {
                @SuppressLint("DefaultLocale") String sql = String.format(
                        "INSERT INTO teams (id, name, country) VALUES('%d', '%s', '%s')",
                        teamId, team.getName(), team.getCountry());
                db.execSQL(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Prida noveho cyklistu do DB
     * @param cyclist
     */
    public void addCyclist(Cyclist cyclist) {
        // Ziskanie ID
        int cyclistId = 0;
        Cursor c = db.rawQuery("SELECT MAX(id) AS _id FROM cyclists", null);
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                cyclistId = c.getInt(c.getColumnIndex("_id")) + 1;
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            c.close();
        }

        // Vlozenie cyklistu do DB
        if (cyclistId > 0) {
            try {
                @SuppressLint("DefaultLocale") String sql = String.format(
                        "INSERT INTO cyclists (" +
                                "id, " +
                                "name, " +
                                "surname, " +
                                "birthYear, " +
                                "teamId, " +
                                "nationality, " +
                                "weight, " +
                                "height) " +
                                "VALUES('%d', '%s', '%s', '%d', '%d', '%s', '%d', '%d')",
                        cyclistId,
                        cyclist.getName(),
                        cyclist.getSurname(),
                        cyclist.getBirthYear(),
                        cyclist.getTeam().getId(),
                        cyclist.getNationality(),
                        cyclist.getWeight(),
                        cyclist.getHeight());
                db.execSQL(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Vymaze cyklistu s danym ID z DB
     * @param cyclistId
     */
    public boolean deleteCyclist(int cyclistId) {
        Cursor c = db.rawQuery("SELECT id FROM cyclists WHERE id = ?", new String[] {String.valueOf(cyclistId)});
        try {
            if (c.getCount() > 0) {
                String sql = "DELETE FROM cyclists WHERE id = " + cyclistId;
                db.execSQL(sql);
                c.close();
                return true;
            } else {
                c.close();
                return false;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        c.close();

        return false;
    }


    /**
     * Vymaze tim s danym ID z DB
     * @param teamId
     */
    public boolean deleteTeam(int teamId) {
        Cursor c = db.rawQuery("SELECT id FROM teams WHERE id = ?", new String[] {String.valueOf(teamId)});
        try {
            // Ak taky tim existuje a nema ziadneho cyklistu
            if (c.getCount() > 0 && getAllCyclists(teamId).size() == 0) {
                String sql = "DELETE FROM teams WHERE id = " + teamId;
                db.execSQL(sql);
                c.close();
                return true;
            } else {
                c.close();
                return false;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        c.close();

        return false;
    }


    /**
     * Zisti ci je databaza prazdna
     * @return
     */
    public boolean isEmpty() {
        Cursor c = db.rawQuery("SELECT count(*) as pocet FROM teams", null);
        c.moveToFirst();
        if (!c.isAfterLast()) {
            int count = c.getInt(c.getColumnIndex("pocet"));

            return count <= 0;
        }
        c.close();

        return false;
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
        values.put("birthYear", "1990");
        values.put("teamId", 4);
        values.put("nationality", "Slovakia");
        values.put("weight", 73);
        values.put("height", 184);
        db.insert("cyclists", null, values);

        values.put("id", 2);
        values.put("name", "Daniel");
        values.put("surname", "Oss");
        values.put("birthYear", "1987");
        values.put("teamId", 4);
        values.put("nationality", "Italy");
        values.put("weight", 75);
        values.put("height", 190);
        db.insert("cyclists", null, values);

        values.put("id", 3);
        values.put("name", "Chris");
        values.put("surname", "Froome");
        values.put("birthYear", "1985");
        values.put("teamId", 13);
        values.put("nationality", "Great Britain");
        values.put("weight", 69);
        values.put("height", 186);
        db.insert("cyclists", null, values);

        values.put("id", 4);
        values.put("name", "Primož");
        values.put("surname", "Roglič");
        values.put("birthYear", "1989");
        values.put("teamId", 14);
        values.put("nationality", "Slovenia");
        values.put("weight", 65);
        values.put("height", 177);
        db.insert("cyclists", null, values);

        values.put("id", 5);
        values.put("name", "Wout");
        values.put("surname", "van Aert");
        values.put("birthYear", "1994");
        values.put("teamId", 14);
        values.put("nationality", "Belgium");
        values.put("weight", 78);
        values.put("height", 187);
        db.insert("cyclists", null, values);

        values.put("id", 6);
        values.put("name", "Steven");
        values.put("surname", "Kruijswijk");
        values.put("birthYear", "1987");
        values.put("teamId", 14);
        values.put("nationality", "Netherlands");
        values.put("weight", 66);
        values.put("height", 178);
        db.insert("cyclists", null, values);

        values.put("id", 7);
        values.put("name", "Tony");
        values.put("surname", "Martin");
        values.put("birthYear", "1985");
        values.put("teamId", 14);
        values.put("nationality", "Germany");
        values.put("weight", 75);
        values.put("height", 185);
        db.insert("cyclists", null, values);

        values.put("id", 8);
        values.put("name", "Dylan");
        values.put("surname", "Groenewegen");
        values.put("birthYear", "1993");
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
        db = null;
        instance = null;
    }
}
