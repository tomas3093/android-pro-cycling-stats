package com.example.semestralka.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class Cyclist implements Parcelable {

    private int id;
    private String name;
    private String surname;
    private int birthYear;   // ISO string
    private Team team;
    private String nationality;
    private int weight;
    private int height;

    public Cyclist(int id, String name, String surname, int birthYear, Team team, String nationality, int weight, int height) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthYear = birthYear;
        this.team = team;
        this.nationality = nationality;
        this.weight = weight;
        this.height = height;
    }

    public Cyclist(Parcel in) {
        id = in.readInt();
        name = in.readString();
        surname = in.readString();
        birthYear = in.readInt();
        team = in.readParcelable(Team.class.getClassLoader());
        nationality = in.readString();
        weight = in.readInt();
        height = in.readInt();
    }

    public static final Creator<Cyclist> CREATOR = new Creator<Cyclist>() {
        @Override
        public Cyclist createFromParcel(Parcel in) {
            return new Cyclist(in);
        }

        @Override
        public Cyclist[] newArray(int size) {
            return new Cyclist[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeInt(birthYear);
        dest.writeParcelable(team, flags);
        dest.writeString(nationality);
        dest.writeInt(weight);
        dest.writeInt(height);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    public String getSurname() {
        return surname;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String getAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        return String.valueOf(currentYear - birthYear);
    }

    public Team getTeam() {
        return team;
    }

    public String getNationality() {
        return nationality;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }


    /**
     * Vrati cestu k nahladovemu obrazku daneho cyklistu
     * @return
     */
    private String getImageUrl() {
        return "cyclists/" + this.id + ".jpg";
    }


    /**
     * Vrati cestu k default nahladovemu obrazku
     * @return
     */
    private String getDefaultImageUrl() {
        return "cyclists/default.jpg";
    }


    /**
     * Vrati nahladovy obrazok daneho cyklistu
     * @param context
     * @return
     */
    public Bitmap getBitmapFromAsset(Context context) {
        AssetManager assetManager = context.getAssets();

        InputStream inputStream;
        Bitmap bitmap = null;

        try {
            inputStream = assetManager.open(getImageUrl());
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {

            // Ak sa nenasiel obrazok cyklistu, hlada sa default
            try {
                inputStream = assetManager.open(getDefaultImageUrl());
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e2) {
                e2.printStackTrace();
            }

        }

        return bitmap;
    }
}
