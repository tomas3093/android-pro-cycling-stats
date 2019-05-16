package com.example.semestralka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.semestralka.model.DataManager;
import com.example.semestralka.model.Team;

import java.util.List;

public class TeamListActivity extends AppCompatActivity implements TeamsViewAdapter.ItemClickListener {

    TeamsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        // Vytvorenie manazera dat
        DataManager dm = DataManager.getInstance(this);

        // Nacitanie example dat
        dm.loadExampleData();

        List<Team> teams = dm.getAllTeams();
        dm.close(); // Zatvorenie spojenia


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvTeams);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamsViewAdapter(this, teams);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
