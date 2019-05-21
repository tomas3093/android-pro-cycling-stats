package com.example.semestralka;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.semestralka.model.DataManager;
import com.example.semestralka.model.Team;


/**
 * Aktivita zabezpecujuca vytvorenie a pridanie noveho timu
 */
public class TeamFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_form);
    }


    /**
     * Kliknutie na tlacidlo ulozit tim
     * @param v
     */
    public void onClickSaveBtn(View v)
    {
        EditText etTeamName = findViewById(R.id.etTeamName);
        EditText etTeamCountry = findViewById(R.id.etTeamCountry);

        String name = etTeamName.getText().toString();
        String country = etTeamCountry.getText().toString();

        Intent resultIntent = new Intent();     // Sprava pre predoslu aktivitu

        // Boli zadane validne data
        if (name.length() > 3 && country.length() > 3) {
            Team team = new Team(0, name, country);

            // Ulozenie timu do DB
            DataManager.getInstance(this).addTeam(team);

            // Sprava pre volajucu aktivitu ze sa pridanie podarilo
            setResult(Activity.RESULT_OK, resultIntent);
        } else {

            // Pridanie sa nepodarilo
            setResult(Activity.RESULT_CANCELED, resultIntent);
        }

        // Ukoncenie aktivity
        finish();
    }
}
