package com.example.semestralka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.semestralka.model.Cyclist;
import com.example.semestralka.model.DataManager;
import com.example.semestralka.model.Team;

import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


/**
 * Zobrazenie zoznamu cyklistov s funkciou filtrovania a pridavania novych
 */
public class CyclistListActivity extends AppCompatActivity implements CyclistsViewAdapter.ItemClickListener {

    CyclistsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclist_list);

        // Ziskanie dat
        DataManager dm = DataManager.getInstance(this);
        List<Cyclist> cyclists = dm.getAllCyclists();

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvCyclists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CyclistsViewAdapter(this, cyclists);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        // Zobrazenie detailu zvoleneho cyklistu
        Intent intent = new Intent(this, CyclistDetailActivity.class);

        // Prenesenie dat do druhej aktivity
        Cyclist selectedCyclist = adapter.getItem(position);
        intent.putExtra(EXTRA_MESSAGE, selectedCyclist);

        startActivity(intent);
    }
}
