package com.example.semestralka;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Zobrazuje detailne informacie o zvolenom cyklistovi
 */
public class CyclistDetailActivity extends AppCompatActivity {

    private static final String STATE_ID_DATA = "stateIdData";

    private Cyclist data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclist_detail);

        // Vytvaranie novej instancie
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                // Ziskanie pozadovaneho timu
                Cyclist cyclist = extras.getParcelable(EXTRA_MESSAGE);
                if (cyclist != null) {
                    data = cyclist;

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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Ulozenie aktualne zobrazeneho cyklistu
        outState.putParcelable(STATE_ID_DATA, data);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    /**
     * Nastavi data do jednotlivych view
     */
    @SuppressLint("DefaultLocale")
    private void setAndRenderView() {
        ImageView ivCyclistThumbnail = findViewById(R.id.ivCyclistThumbnail);
        TextView tvCyclistFullName = findViewById(R.id.tvCyclistFullName);
        TextView tvTeamName = findViewById(R.id.tvTeamName);
        TextView tvNationality = findViewById(R.id.tvNationality);
        TextView tvBirthDate = findViewById(R.id.tvBirthDate);
        TextView tvAge = findViewById(R.id.tvAge);
        TextView tvWeight = findViewById(R.id.tvWeight);
        TextView tvHeight = findViewById(R.id.tvHeight);

        ivCyclistThumbnail.setImageBitmap(data.getBitmapFromAsset(this));
        tvCyclistFullName.setText(data.getFullName());
        tvTeamName.setText(String.format("Current team: %s", data.getTeam().getName()));
        tvNationality.setText(String.format("Nationality: %s", data.getNationality()));
        tvBirthDate.setText(String.format("Year of birth: %d", data.getBirthYear()));
        tvAge.setText(String.format("Age: %s", data.getAge()));
        tvWeight.setText(String.format("Weight: %d kg", data.getWeight()));
        tvHeight.setText(String.format("Height: %d cm", data.getHeight()));
    }


    /**
     * Odstranenie z DB
     * @param v
     */
    public void onClickDeleteBtn(View v)
    {
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(CyclistDetailActivity.this);
        builder.setMessage("Do you really want to delete this cyclist?").setTitle("Delete cyclist");

        // Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK

                // Delete cyclist
                DataManager.getInstance(context).deleteCyclist(data.getId());
                // Sprava pre volajucu aktivitu ze sa pridanie podarilo
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);

                // Ukoncenie aktivity
                ((CyclistDetailActivity) context).finish();
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
}
