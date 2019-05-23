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
    private Integer ageFrom;
    private Integer ageTo;
    private Team team;
    private String nationality;
    private Integer weightFrom;
    private Integer heightFrom;
    private Integer weightTo;
    private Integer heightTo;


    public SearchCriterion(String name, String surname, Integer ageFrom, Integer ageTo, Team team, String nationality, Integer weightFrom, Integer heightFrom, Integer weightTo, Integer heightTo) {
        this.name = name;
        this.surname = surname;
        this.ageFrom = ageFrom != null ? ageFrom : 0;
        this.ageTo = ageTo != null ? ageTo : 999;
        this.team = team;
        this.nationality = nationality;
        this.weightFrom = weightFrom != null ? weightFrom : 0;
        this.heightFrom = heightFrom != null ? heightFrom : 0;
        this.weightTo = weightTo != null ? weightTo : 999;
        this.heightTo = heightTo != null ? heightTo : 999;
    }


    /**
     * Vrati true ak cyklista splna vsetky nastavene atributy kriteria, inak false
     * @return
     */
    public boolean isCyclistValid(Cyclist cyclist) {
        boolean nameCrit;
        boolean surnameCrit;
        boolean ageCrit;
        boolean teamCrit;
        boolean nationalityCrit;
        boolean weightCrit;
        boolean heightCrit;

        nameCrit = name == null || cyclist.getName().equalsIgnoreCase(this.name);
        surnameCrit = surname == null || cyclist.getSurname().equalsIgnoreCase(this.surname);
        teamCrit = team == null || cyclist.getTeam().getId() == team.getId();
        ageCrit = Integer.valueOf(cyclist.getAge()) >= ageFrom && Integer.valueOf(cyclist.getAge()) <= ageTo;
        nationalityCrit = nationality == null || cyclist.getNationality().equalsIgnoreCase(nationality);
        weightCrit = cyclist.getWeight() >= weightFrom && cyclist.getWeight() <= weightTo;
        heightCrit = cyclist.getHeight() >= heightFrom && cyclist.getHeight() <= heightTo;

        return nameCrit && surnameCrit && ageCrit && teamCrit && nationalityCrit && weightCrit && heightCrit;
    }


    protected SearchCriterion(Parcel in) {
        name = in.readString();
        surname = in.readString();
        if (in.readByte() == 0) {
            ageFrom = null;
        } else {
            ageFrom = in.readInt();
        }
        if (in.readByte() == 0) {
            ageTo = null;
        } else {
            ageTo = in.readInt();
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
        if (ageFrom == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ageFrom);
        }
        if (ageTo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ageTo);
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
