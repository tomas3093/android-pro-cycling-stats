package com.example.semestralka;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.semestralka.model.Cyclist;
import com.example.semestralka.model.DataManager;
import com.example.semestralka.model.Team;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Trieda umoznuje zobrazit formular s fieldami pre pridanie noveho cyklistu do DB
 */
public class CyclistFormActivity extends AppCompatActivity {

    AutoCompleteTextView tvTeamPicker;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclist_form);

        // Get a reference to the AutoCompleteTextView in the layout
        tvTeamPicker = findViewById(R.id.tvTeamPicker);

        // Get the string array
        String[] teamNames = DataManager.getInstance(this).getAllTeamNames();

        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teamNames);
        tvTeamPicker.setAdapter(adapter);
    }


    /**
     * Kliknutie na tlacidlo ulozit cyklistu
     * @param v
     */
    public void onClickSaveBtn(View v)
    {
        EditText etCyclistName = findViewById(R.id.etCyclistName);
        EditText etCyclistSurname = findViewById(R.id.etCyclistSurname);
        EditText etCyclistBirthYear = findViewById(R.id.etCyclistBirthYear);
        EditText etCyclistNationality = findViewById(R.id.etCyclistNationality);
        EditText etCyclistWeight = findViewById(R.id.etCyclistWeight);
        EditText etCyclistHeight = findViewById(R.id.etCyclistHeight);

        String name = etCyclistName.getText().toString();
        String surname = etCyclistSurname.getText().toString();
        String birthYear = etCyclistBirthYear.getText().toString();
        String teamName = tvTeamPicker.getText().toString();
        String nationality = etCyclistNationality.getText().toString();
        int weight = Integer.valueOf(etCyclistWeight.getText().toString());
        int height = Integer.valueOf(etCyclistHeight.getText().toString());

        Intent resultIntent = new Intent();     // Sprava pre predoslu aktivitu
        Team cyclistTeam = DataManager.getInstance(this).getTeam(teamName);

        // Boli zadane validne data
        if (    name.length() > 3 &&
                surname.length() > 3 &&
                birthYear.length() == 4 &&
                cyclistTeam != null &&
                nationality.length() > 3 &&
                weight > 0 &&
                height > 0) {

            try {
                Cyclist cyclist = new Cyclist(0, name, surname, Integer.parseInt(birthYear), cyclistTeam, nationality, weight, height);

                // Ulozenie cyklistu do DB
                DataManager.getInstance(this).addCyclist(cyclist);

                // Sprava pre volajucu aktivitu ze sa pridanie podarilo
                setResult(Activity.RESULT_OK, resultIntent);
            } catch (NumberFormatException e) {
                e.printStackTrace();

                // Pridanie sa nepodarilo
                setResult(Activity.RESULT_CANCELED, resultIntent);
            }

        } else {

            // Pridanie sa nepodarilo
            setResult(Activity.RESULT_CANCELED, resultIntent);
        }

        // Ukoncenie aktivity
        finish();
    }
}
