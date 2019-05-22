package com.example.semestralka.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Trieda reprezentujuca vyhladavacie kriteria cyklistov
 * ak dany atribut nie je nastaveny vo vyhladavacom filtri, jeho hodnota je null
 */
public class SearchCriterion implements Parcelable {

    private String name;
    private String surname;
    private Integer birthYear;
    private Team team;
    private String nationality;
    private Integer weightFrom;
    private Integer heightFrom;
    private Integer weightTo;
    private Integer heightTo;

    public SearchCriterion(String name, String surname, Integer birthYear, Team team, String nationality, Integer weightFrom, Integer heightFrom, Integer weightTo, Integer heightTo) {
        this.name = name;
        this.surname = surname;
        this.birthYear = birthYear;
        this.team = team;
        this.nationality = nationality;
        this.weightFrom = weightFrom;
        this.heightFrom = heightFrom;
        this.weightTo = weightTo;
        this.heightTo = heightTo;
    }


    protected SearchCriterion(Parcel in) {
        name = in.readString();
        surname = in.readString();
        if (in.readByte() == 0) {
            birthYear = null;
        } else {
            birthYear = in.readInt();
        }
        team = in.readParcelable(Team.class.getClassLoader());
        nationality = in.readString();
        if (in.readByte() == 0) {
            weightFrom = null;
        } else {
            weightFrom = in.readInt();
        }
        if (in.readByte() == 0) {
            heightFrom = null;
        } else {
            heightFrom = in.readInt();
        }
        if (in.readByte() == 0) {
            weightTo = null;
        } else {
            weightTo = in.readInt();
        }
        if (in.readByte() == 0) {
            heightTo = null;
        } else {
            heightTo = in.readInt();
        }
    }

    public static final Creator<SearchCriterion> CREATOR = new Creator<SearchCriterion>() {
        @Override
        public SearchCriterion createFromParcel(Parcel in) {
            return new SearchCriterion(in);
        }

        @Override
        public SearchCriterion[] newArray(int size) {
            return new SearchCriterion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(surname);
        if (birthYear == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(birthYear);
        }
        dest.writeParcelable(team, flags);
        dest.writeString(nationality);
        if (weightFrom == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(weightFrom);
        }
        if (heightFrom == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(heightFrom);
        }
        if (weightTo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(weightTo);
        }
        if (heightTo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(heightTo);
        }
    }
}
