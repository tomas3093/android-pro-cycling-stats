package com.example.semestralka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semestralka.model.Cyclist;
import com.example.semestralka.model.DataManager;
import com.example.semestralka.model.Team;

import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


/**
 * Zobrazenie detailu zvoleneho timu zo zoznamu timov
 */
public class TeamDetailActivity extends AppCompatActivity implements CyclistsViewAdapter.ItemClickListener {

    CyclistsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);

        TextView txtViewTeamName = findViewById(R.id.tvTeamName);
        TextView txtViewTeamCountry = findViewById(R.id.tvTeamCountry);
        ImageView imgViewTeamThumbnail = findViewById(R.id.ivTeamThumbnail);

        // Ziskanie pozadovaneho timu
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if (extras != null) {

                Team team = extras.getParcelable(EXTRA_MESSAGE);
                if (team != null) {
                    txtViewTeamName.setText(team.getName());
                    txtViewTeamCountry.setText(team.getCountry());
                    imgViewTeamThumbnail.setImageBitmap(team.getBitmapFromAsset(this));

                    // Ziskanie cyklistov z daneho timu
                    DataManager dm = DataManager.getInstance(this);
                    List<Cyclist> cyclists = dm.getAllCyclists(team.getId());

                    // set up the RecyclerView
                    RecyclerView recyclerView = findViewById(R.id.rvCyclists);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    adapter = new CyclistsViewAdapter(this, cyclists);
                    adapter.setClickListener(this);
                    recyclerView.setAdapter(adapter);
                }
            }
        }
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
