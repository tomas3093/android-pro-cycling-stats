package com.example.semestralka;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.semestralka.model.DataManager;


/**
 * Hlavna aktivita volana pri spusteni aplikacie
 */
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE_DATA = "intent_extra_message_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vytvorenie manazera dat
        DataManager dm = DataManager.getInstance(this);

        // Nacitanie example dat ak je DB prazdna
        if (dm.isEmpty())
            dm.loadExampleData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Zatvorenie spojenia
        DataManager.getInstance(this).close();
    }


    /**
     * Zobrazenie zoznamu cyklistov
     * @param v
     */
    public void onClickCyclistsBtn(View v)
    {
        Intent intent = new Intent(this, CyclistListActivity.class);

        // Nastavenie filtrovacieho kriteria
        intent.putExtra(EXTRA_MESSAGE_DATA, (Parcelable) null);

        startActivity(intent);
    }


    /**
     * Zobrazenie zoznamu timov
     * @param v
     */
    public void onClickTeamsBtn(View v)
    {
        Intent intent = new Intent(this, TeamListActivity.class);

        // Prenesenie dat do druhej aktivity
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE_DATA, message);

        startActivity(intent);
    }


    /**
     * Zobrazenie aktivity s vyhladavacim filtrom cyklistov
     * @param v
     */
    public void onClickSearchBtn(View v)
    {
        Intent intent = new Intent(this, CyclistSearchActivity.class);
        startActivity(intent);
    }


    /**
     * Stlacenie tlacidla exit - ukoncenie aplikacie
     * @param v
     */
    public void onClickExitBtn(View v)
    {
        this.finish();
        System.exit(0);
    }
}
