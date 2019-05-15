package com.example.semestralka.model;

import java.util.Date;

public class Cyclist {

    private int id;
    private String name;
    private String surname;
    private Date bornDate;
    private Team team;
    private String nationality;
    private int weight;
    private int height;

    public Cyclist(int id, String name, String surname, Date bornDate, Team team, String nationality, int weight, int height) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.bornDate = bornDate;
        this.team = team;
        this.nationality = nationality;
        this.weight = weight;
        this.height = height;
    }
}
