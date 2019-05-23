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

import com.example.semestralka.model.Cyclist;
import com.example.semestralka.model.DataManager;
import com.example.semestralka.model.SearchCriterion;
import com.example.semestralka.model.Team;

import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


/**
 * Zobrazenie zoznamu cyklistov s funkciou filtrovania a pridavania novych
 */
public class CyclistListActivity extends AppCompatActivity implements CyclistsViewAdapter.ItemClickListener {

    private static final String STATE_ID_DATA = "stateIdData";

    private static final int REQUEST_CODE_CYCLIST_ADD = 1;
    private static final int REQUEST_CODE_CYCLIST_DELETE = 2;

    CyclistsViewAdapter adapter;
    SearchCriterion criterion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclist_list);

        // Vytvaranie novej instancie
        if (savedInstanceState == null) {
            // Filtrovacie kriterium
            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                // Ziskanie pozadovaneho kriteria
                criterion = extras.getParcelable(MainActivity.EXTRA_MESSAGE_DATA);
            } else {
                criterion = null;
            }
        } else {
            // Obnova zrusenej instancie
            criterion = savedInstanceState.getParcelable(STATE_ID_DATA);
        }

        // Nacitanie dat
        loadCyclistsToRecyclerView(criterion);
    }


    /**
     * Nacita nanovo data do zoznamu
     */
    private void loadCyclistsToRecyclerView(SearchCriterion criterion) {

        // Ziskanie dat
        DataManager dm = DataManager.getInstance(this);
        List<Cyclist> cyclists;
        if (criterion == null) {
            // Nacitanie vsetkych cyklistov bez filtrovania
            cyclists = dm.getAllCyclists();
        } else {
            // Nacitanie vyfiltrovanych cyklistov
            cyclists = dm.getFilteredCyclists(criterion);
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvCyclists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CyclistsViewAdapter(this, cyclists);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Ulozenie aktualne zobrazeneho cyklistu
        outState.putParcelable(STATE_ID_DATA, criterion);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClick(View view, int position) {
        // Zobrazenie detailu zvoleneho cyklistu
        Intent intent = new Intent(this, CyclistDetailActivity.class);

        // Prenesenie dat do druhej aktivity
        Cyclist selectedCyclist = adapter.getItem(position);
        intent.putExtra(EXTRA_MESSAGE, selectedCyclist);

        // Spustenie aktivity s cakanim na vysledok ci user vymaze cyklistu
        startActivityForResult(intent, REQUEST_CODE_CYCLIST_DELETE);
    }


    /**
     * Kliknutie na tlacidlo pridat -> spusti sa nova aktivita s formularom
     * @param v
     */
    public void onClickAddBtn(View v)
    {
        Intent intent = new Intent(this, CyclistFormActivity.class);

        // Spustenie aktivity s cakanim na vysledok ci sa pridanie podarilo
        startActivityForResult(intent, REQUEST_CODE_CYCLIST_ADD);
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
            // Poziadavka na pridanie cyklistu
            case REQUEST_CODE_CYCLIST_ADD:
                if (resultCode == Activity.RESULT_OK) {     // Pridanie sa podarilo
                    // Aktualizovanie zoznamu cyklistov
                    loadCyclistsToRecyclerView(criterion);
                    Toast.makeText(this, "Cyclists saved!", Toast.LENGTH_LONG).show();
                } else if (resultCode == Activity.RESULT_CANCELED) {    // Pridanie zlyhalo
                    // Do nothing
                    Toast.makeText(this, "Action canceled!", Toast.LENGTH_LONG).show();
                }
                break;

            // Poziadavka na vymazanie cyklistu
            case REQUEST_CODE_CYCLIST_DELETE:
                if (resultCode == Activity.RESULT_OK) {     // Vymazanie sa podarilo
                    // Aktualizovanie zoznamu cyklistov
                    loadCyclistsToRecyclerView(criterion);
                    Toast.makeText(this, "Cyclist deleted!", Toast.LENGTH_LONG).show();
                } else if (resultCode == Activity.RESULT_CANCELED) {    // Vymazanie zlyhalo
                    // Do nothing
                    //Toast.makeText(this, "Action canceled!", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;

        }
    }
}
