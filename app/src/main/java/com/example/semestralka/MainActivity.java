package com.example.semestralka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.semestralka.model.DataManager;


/**
 * Hlavna aktivita volana pri spusteni aplikacie
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vytvorenie manazera dat
        DataManager dm = DataManager.getInstance(this);

        // Nacitanie example dat
        dm.loadExampleData();

        //Team t = l.get(1);
        //Toast.makeText(this, "First team " + t.getName(), Toast.LENGTH_LONG).show();
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
        //intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);
    }


    /**
     * Zobrazenie zoznamu noviniek
     * @param v
     */
    public void onClickNewsBtn(View v)
    {
        // TODO
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
