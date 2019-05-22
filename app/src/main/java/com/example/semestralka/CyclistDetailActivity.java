package com.example.semestralka;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semestralka.model.Cyclist;
import com.example.semestralka.model.DataManager;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class CyclistDetailActivity extends AppCompatActivity {

    private int cyclistId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclist_detail);

        ImageView ivCyclistThumbnail = findViewById(R.id.ivCyclistThumbnail);
        TextView tvCyclistFullName = findViewById(R.id.tvCyclistFullName);
        TextView tvTeamName = findViewById(R.id.tvTeamName);
        TextView tvNationality = findViewById(R.id.tvNationality);
        TextView tvBirthDate = findViewById(R.id.tvBirthDate);
        TextView tvAge = findViewById(R.id.tvAge);
        TextView tvWeight = findViewById(R.id.tvWeight);
        TextView tvHeight = findViewById(R.id.tvHeight);

        // Ziskanie pozadovaneho timu
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if (extras != null) {

                Cyclist cyclist = extras.getParcelable(EXTRA_MESSAGE);
                if (cyclist != null) {
                    cyclistId = cyclist.getId();
                    ivCyclistThumbnail.setImageBitmap(cyclist.getBitmapFromAsset(this));
                    tvCyclistFullName.setText(cyclist.getFullName());
                    tvTeamName.setText("Current team: " + cyclist.getTeam().getName());
                    tvNationality.setText("Nationality: " + cyclist.getNationality());
                    tvBirthDate.setText("Year of birth: " + cyclist.getBirthYear());
                    tvAge.setText("Age: " + cyclist.getAge());
                    tvWeight.setText("Weight: " + cyclist.getWeight() + " kg");
                    tvHeight.setText("Height: " + cyclist.getHeight() + " cm");
                }
            }
        }

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
                DataManager.getInstance(context).deleteCyclist(cyclistId);
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
