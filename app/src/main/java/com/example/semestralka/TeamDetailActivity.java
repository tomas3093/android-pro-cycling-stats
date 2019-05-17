package com.example.semestralka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semestralka.model.Team;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


/**
 * Zobrazenie detailu zvoleneho timu zo zoznamu timov
 */
public class TeamDetailActivity extends AppCompatActivity {

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
                }
            }
        }
    }
}
