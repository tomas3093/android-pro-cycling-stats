package com.example.semestralka.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.InputStream;


/**
 * Trieda reprezentuje cyklisticky tim
 */
public class Team implements Parcelable {

    private int id;
    private String name;
    private String country;

    Team(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    Team(Parcel in) {
        id = in.readInt();
        name = in.readString();
        country = in.readString();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }


    /**
     * Vrati cestu k nahladovemu obrazku daneho timu
     * @return
     */
    private String getImageUrl() {
        return "teams/" + this.id + ".png";
    }


    /**
     * Vrati nahladovy obrazok daneho tymu
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
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(country);
    }
}
