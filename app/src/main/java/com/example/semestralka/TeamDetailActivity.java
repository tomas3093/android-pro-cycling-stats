package com.example.semestralka;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semestralka.model.Cyclist;
import com.example.semestralka.model.DataManager;
import com.example.semestralka.model.Team;

import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


/**
 * Zobrazenie detailu zvoleneho timu zo zoznamu timov
 */
public class TeamDetailActivity extends AppCompatActivity implements CyclistsViewAdapter.ItemClickListener {

    private static final int REQUEST_CODE_MEMBER_DELETE = 1;

    private static final String STATE_ID_DATA = "stateIdData";

    private CyclistsViewAdapter adapter;
    private Team data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);

        // Vytvaranie novej instancie
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                // Ziskanie pozadovaneho timu
                Team team = extras.getParcelable(EXTRA_MESSAGE);
                if (team != null) {
                    data = team;

                    // Nacitanie a vykreslenie dat
                    setAndRenderView();
                }
            }
        } else {
            // Obnova zrusenej instancie
            data = savedInstanceState.getParcelable(STATE_ID_DATA);

            // Nacitanie a vykreslenie dat
            setAndRenderView();
        }
    }


    /**
     * Volane pri zmene konfiguracie; ulozenie zobrazenych dat
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Ulozenie aktualne zobrazeneho timu
        outState.putParcelable(STATE_ID_DATA, data);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    /**
     * Nastavi data do jednotlivych view a nacita cyklistov z daneho timu
     */
    private void setAndRenderView() {
        TextView txtViewTeamName = findViewById(R.id.tvTeamName);
        TextView txtViewTeamCountry = findViewById(R.id.tvTeamCountry);
        ImageView imgViewTeamThumbnail = findViewById(R.id.ivTeamThumbnail);

        txtViewTeamName.setText(data.getName());
        txtViewTeamCountry.setText(data.getCountry());
        imgViewTeamThumbnail.setImageBitmap(data.getBitmapFromAsset(this));

        // Ziskanie cyklistov z daneho timu
        DataManager dm = DataManager.getInstance(this);
        List<Cyclist> cyclists = dm.getAllCyclists(data.getId());

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

        startActivityForResult(intent, REQUEST_CODE_MEMBER_DELETE);
    }


    /**
     * Odstranenie z DB
     * @param v
     */
    public void onClickDeleteBtn(View v)
    {
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(TeamDetailActivity.this);
        builder.setMessage("Do you really want to delete this team?").setTitle("Delete team");

        // Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK

                // Delete team
                boolean wasDeleted = DataManager.getInstance(context).deleteTeam(data.getId());
                if (wasDeleted) {
                    // Sprava pre volajucu aktivitu ze sa zmazanie podarilo
                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);

                    // Ukoncenie aktivity
                    ((TeamDetailActivity) context).finish();
                } else {
                    // Zmazanie sa nepodarilo
                    Toast.makeText(context, "Can't delete team which has members!", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
            // Poziadavka na vymazanie clena timu
            case REQUEST_CODE_MEMBER_DELETE:
                if (resultCode == Activity.RESULT_OK) {     // Vymazanie sa podarilo
                    // Aktualizovanie zoznamu timov
                    setAndRenderView();
                    Toast.makeText(this, "Team member deleted!", Toast.LENGTH_LONG).show();
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
