package com.example.semestralka;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.semestralka.model.DataManager;
import com.example.semestralka.model.SearchCriterion;
import com.example.semestralka.model.Team;

import static com.example.semestralka.MainActivity.EXTRA_MESSAGE_DATA;

public class CyclistSearchActivity extends AppCompatActivity {

    AutoCompleteTextView tvTeamPicker;
    AutoCompleteTextView tvNationalityPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclist_search);

        // Nastavenie Autocomplete pre timy a narodnosti
        tvTeamPicker = findViewById(R.id.tvTeamPicker);
        tvNationalityPicker = findViewById(R.id.tvNationalityPicker);

        // Get the string arrays
        String[] teamNames = DataManager.getInstance(this).getAllTeamNames();
        String[] nationalities = DataManager.getInstance(this).getAllNationalities();

        // Create the adapters and set them to the AutoCompleteTextViews
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teamNames);
        tvTeamPicker.setAdapter(adapter);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nationalities);
        tvNationalityPicker.setAdapter(adapter);
    }


    /**
     * Zobrazenie vyfiltrovaneho zoznamu cyklistov
     * @param v
     */
    public void onClickSearchBtn(View v)
    {
        EditText name_field = findViewById(R.id.name_field);
        EditText surname_field = findViewById(R.id.surname_field);
        EditText age_from_field = findViewById(R.id.age_from_field);
        EditText age_to_field = findViewById(R.id.age_to_field);
        EditText weight_from_field = findViewById(R.id.weight_from_field);
        EditText weight_to_field = findViewById(R.id.weight_to_field);
        EditText height_from_field = findViewById(R.id.height_from_field);
        EditText height_to_field = findViewById(R.id.height_to_field);

        String nameCrit = name_field.getText().toString().trim().length() > 0 ? name_field.getText().toString().trim() : null;
        String surnameCrit = surname_field.getText().toString().trim().length() > 0 ? surname_field.getText().toString().trim() : null;
        Integer ageFromCrit = age_from_field.getText().toString().trim().length() > 0 ? Integer.valueOf(age_from_field.getText().toString().trim()) : null;
        Integer ageToCrit = age_to_field.getText().toString().length() > 0 ? Integer.valueOf(age_to_field.getText().toString().trim()) : null;
        Team teamCrit = DataManager.getInstance(this).getTeam(tvTeamPicker.getText().toString());
        String nationalityCrit = tvNationalityPicker.getText().toString().trim().length() > 0 ? tvNationalityPicker.getText().toString() : null;
        Integer weightFromCrit = weight_from_field.getText().toString().length() > 0 ? Integer.valueOf(weight_from_field.getText().toString().trim()) : null;
        Integer weightToCrit = weight_to_field.getText().toString().length() > 0 ? Integer.valueOf(weight_to_field.getText().toString().trim()) : null;
        Integer heightFromCrit = height_from_field.getText().toString().length() > 0 ? Integer.valueOf(height_from_field.getText().toString().trim()) : null;
        Integer heightToCrit = height_to_field.getText().toString().length() > 0 ? Integer.valueOf(height_to_field.getText().toString().trim()) : null;


        // Nastavenie filtrovacieho kriteria
        SearchCriterion criterion = new SearchCriterion(nameCrit, surnameCrit, ageFromCrit, ageToCrit, teamCrit, nationalityCrit, weightFromCrit, heightFromCrit, weightToCrit, heightToCrit);

        Intent intent = new Intent(this, CyclistListActivity.class);
        intent.putExtra(EXTRA_MESSAGE_DATA, criterion);

        startActivity(intent);
    }
}
