package com.example.semestralka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.semestralka.model.DataManager;
import com.example.semestralka.model.Team;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vytvorenie manazera dat
        DataManager dm = DataManager.getInstance(this);

        // Nacitanie example dat
        dm.loadExampleData();

        List<Team> l = dm.getAllTeams();
        dm.close(); // Zatvorenie spojenia

        Team t = l.get(1);
        Toast.makeText(this, "First team " + t.getName(), Toast.LENGTH_LONG).show();
    }

    public void onClickExitBtn(View v)
    {
        this.finish();
        System.exit(0);
    }
}
