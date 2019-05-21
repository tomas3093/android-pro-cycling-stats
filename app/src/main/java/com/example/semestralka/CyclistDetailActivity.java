package com.example.semestralka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semestralka.model.Cyclist;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class CyclistDetailActivity extends AppCompatActivity {

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
}
