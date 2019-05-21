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
import com.example.semestralka.model.Team;

import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


/**
 * Zobrazenie zoznamu cyklistov s funkciou filtrovania a pridavania novych
 */
public class CyclistListActivity extends AppCompatActivity implements CyclistsViewAdapter.ItemClickListener {

    private static final int REQUEST_CODE_CYCLIST_ADD = 1;

    CyclistsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclist_list);

        // Nacitanie dat
        loadCyclistsToRecyclerView();
    }


    /**
     * Nacita nanovo data do zoznamu
     */
    private void loadCyclistsToRecyclerView() {

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
            case REQUEST_CODE_CYCLIST_ADD:
                if (resultCode == Activity.RESULT_OK) {     // Pridanie sa podarilo
                    // Aktualizovanie zoznamu timov
                    loadCyclistsToRecyclerView();
                    Toast.makeText(this, "Cyclists saved!", Toast.LENGTH_LONG).show();
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
