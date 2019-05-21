package com.example.semestralka;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.semestralka.model.DataManager;
import com.example.semestralka.model.Team;

import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


/**
 * Zobrazenie zoznamu vsetkych timov
 */
public class TeamListActivity extends AppCompatActivity implements TeamsViewAdapter.ItemClickListener {

    private static final int REQUEST_CODE_TEAM_ADD = 1;

    TeamsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        // Nacitanie dat
        loadTeamsToRecyclerView();
    }


    /**
     * Nacita nanovo data do zoznamu
     */
    private void loadTeamsToRecyclerView() {

        // Ziskanie dat
        DataManager dm = DataManager.getInstance(this);
        List<Team> teams = dm.getAllTeams();

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvTeams);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamsViewAdapter(this, teams);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();

        // Zobrazenie detailu zvoleneho timu
        Intent intent = new Intent(this, TeamDetailActivity.class);

        // Prenesenie dat do druhej aktivity
        Team selectedTeam = adapter.getItem(position);
        intent.putExtra(EXTRA_MESSAGE, selectedTeam);

        startActivity(intent);
    }


    /**
     * Kliknutie na tlacidlo pridat -> spusti sa nova aktivita s formularom
     * @param v
     */
    public void onClickAddBtn(View v)
    {
        Intent intent = new Intent(this, TeamFormActivity.class);

        // Spustenie aktivity s cakanim na vysledok ci sa pridanie podarilo
        startActivityForResult(intent, REQUEST_CODE_TEAM_ADD);
    }


    /**
     * Callback, ktory sa zavola ked pride odpoved z volanej aktivity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case REQUEST_CODE_TEAM_ADD:
                if (resultCode == Activity.RESULT_OK) {     // Pridanie sa podarilo
                    // Aktualizovanie zoznamu timov
                    loadTeamsToRecyclerView();
                    Toast.makeText(this, "Team saved!", Toast.LENGTH_LONG).show();
                } else if (resultCode == Activity.RESULT_CANCELED) {    // Pridanie zlyhalo
                    // Do nothing
                    Toast.makeText(this, "Action canceled!", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;

        }
    }
}
