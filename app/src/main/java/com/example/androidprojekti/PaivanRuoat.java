package com.example.androidprojekti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Luokka, joka sisältää PäivänRuoat näkymän toiminnot
 * @author Oliver Hamberg
 * @author Joona Nylander
 * @author Niklas Kukkonen
 * @since  12.11.2020
 */
public class PaivanRuoat extends AppCompatActivity {
    private View popupView;
    private AlertDialog popup;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paivan_ruoat);

        //Asetetaan näkyviin lista jo syötyjä aterioita
        ListView ateriaList = findViewById(R.id.list);
        adapter = new ArrayAdapter<Ateria>(this, android.R.layout.simple_list_item_1, Ateriat.getInstance().getAteriat());
        ateriaList.setAdapter(adapter);
    }

    /**
     * Funktio aktivoituu napin painalluksella ja avaa näkymän, josta käyttäjä pääsee lisäämään aterian.
     * @param v PäivänRuoat näkymä
     */
    public void lisaaAteria(View v){
        //Avataan ateria "kysely" näkymä, jossa kysytään aterian perustietoja
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        popupView = getLayoutInflater().inflate(R.layout.alert_ruoka, null);
        builder.setView(popupView);
        popup = builder.create();
        popup.show();
    }

    /**
     * Haetaan tiedot lisaaAteriat() komennon avaamasta näkymästä ja lisätään tämä ateria --> ateriat listaan
     * @param v
     */
    public void tallennaRuoka(View v){
        //Haetaan tiedot ateria "kyselystä"
        String rnimi = ((EditText) popupView.findViewById(R.id.pNimi)).getText().toString();
        String rkalori = ((EditText) popupView.findViewById(R.id.r_kalori)).getText().toString();
        String jnimi = ((EditText) popupView.findViewById(R.id.juoma)).getText().toString();
        String jkalori = ((EditText) popupView.findViewById(R.id.tavoite)).getText().toString();

        //Jos jotain kentistä ei ole täytetty
        if(rnimi.equals("") || rkalori.equals("") || jnimi.equals("") || jkalori.equals("")){
            Toast.makeText(this, "Täytä kaikki kentät", Toast.LENGTH_LONG).show();
        }else {
            try {
                Ateria ateria = new Ateria(rnimi, Integer.parseInt(rkalori), jnimi, Integer.parseInt(jkalori));
                Ateriat.getInstance().lisaaAteria(ateria, this);
                popup.cancel();
            }
            catch(Exception e){
                //Jos käyttäjä asettaa tekstiä kenttiin, jotka ottavat vain lukuja vastaan.
                Toast.makeText(this, "Täytä kaikki kentät! Huomioi, että kalorikentät ottavat vastaan vain lukuja!", Toast.LENGTH_LONG).show();
            }
        }
        adapter.notifyDataSetChanged();
    }
}
