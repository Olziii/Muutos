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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Luokka, joka sisältää PäivänUrheilu näkymän toiminnot
 * @author Oliver Hamberg
 * @author Joona Nylander
 * @author Niklas Kukkonen
 * @since  11.12.2020
 */
public class PaivanUrheilu extends AppCompatActivity {
    private View popupView;
    private AlertDialog popup;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paivan_urheilu);
        ListView urheiluList = findViewById(R.id.list);
        adapter = new ArrayAdapter<UrheiluSuoritus>(this, android.R.layout.simple_list_item_1, UrheiluSuoritukset.getInstance().getSuoritukset());
        urheiluList.setAdapter(adapter);
    }
    /**
     * Funktio aktivoituu napin painalluksella ja avaa näkymän, josta käyttäjä pääsee lisäämään suorituksen.
     * @param v PäivänUrheilu näkymä
     */
    public void lisaaSuoritus(View v){
        //Avataan suoritus "kysely" näkymä, jossa kysytään suorituksen perustietoja
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        popupView = getLayoutInflater().inflate(R.layout.alert_urheilu, null);
        builder.setView(popupView);
        popup = builder.create();
        popup.show();
    }
    /**
     * Haetaan tiedot lisaaSuoritus() komennon avaamasta näkymästä ja lisätään tämä urheilusuoritus --> urheilusuoritukset listaan
     * @param v PäivänUrheilu näkymä
     */
    public void tallennaSuoritus(View v){
        //Haetaan tiedot suoritus "kyselystä"
        String urheilulaji = ((EditText) popupView.findViewById(R.id.urheilusuoritus)).getText().toString();
        String pituus = ((EditText) popupView.findViewById(R.id.pituus)).getText().toString();
        String rasitustaso = ((EditText) popupView.findViewById(R.id.rasitustaso)).getText().toString();

        //Jos käyttäjä ei täyttänyt kenttiä
        if(urheilulaji.equals("") || pituus.equals("") || rasitustaso.equals("")){
            Toast.makeText(this, "Täytä kaikki kentät", Toast.LENGTH_LONG).show();
        }else{
            try {
                UrheiluSuoritus urheiluSuoritus = new UrheiluSuoritus(urheilulaji, Integer.parseInt(pituus), Integer.parseInt(rasitustaso));
                UrheiluSuoritukset.getInstance().lisaaSuoritus(urheiluSuoritus, this);
                popup.cancel();
            }
            catch (Exception e){
                //Jos pituus kenttään asetettiin tekstiä, vaikka se ottaa vastaan vain lukuja
                Toast.makeText(this, "Täytä kaikki kentät! Huomioi, että pituus ottaa vastaan vain lukuja!", Toast.LENGTH_LONG).show();
            }
        }
        adapter.notifyDataSetChanged();
    }
}