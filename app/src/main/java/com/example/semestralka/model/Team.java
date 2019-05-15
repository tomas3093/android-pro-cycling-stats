package com.example.semestralka.model;

public class Team {

    private int id;
    private String name;
    private String country;

    public Team(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
