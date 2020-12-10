package com.example.androidprojekti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;
/**
 * Luokka, joka sisältää PäivänTavoitteet näkymän toiminnot
 */
public class PaivanTavoitteet extends AppCompatActivity {
    ListView tavoiteList;
    private int sijainti;
    ArrayAdapter adapter;
    private View popupView;
    private AlertDialog popup;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paivan_tavoitteet);

        //Asetetaan aika
        TextView paiva = findViewById(R.id.dateView);
        paiva.setText(Aika.getAika());

        //Asetetaan ruudulle ListView elementti, jossa näkyy kaikki tavoitteet.
        tavoiteList = findViewById(R.id.list);
        adapter = new ArrayAdapter<Tavoite>(this, android.R.layout.simple_list_item_1, Tavoitteet.getInstance().getTavoitteet());
        tavoiteList.setAdapter(adapter);
        //Jos tavoitetta painaa, aukeaa tarkemmat tiedot ja mahdollisuus poistaa tai merkata tavoite valmiiksi.
        tavoiteList.setOnItemClickListener((parent, view, position, id) -> {
            sijainti = position;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            popupView = getLayoutInflater().inflate(R.layout.alert_tavoite_check, null);
            builder.setView(popupView);
            popup = builder.create();
            popup.show();
            TextView tavoite = popupView.findViewById(R.id.Tavoite);
            tavoite.setText(Tavoitteet.getInstance().getTavoitteet().get(position).getTavoite());
        });
    }
    /**
     * Tavoitteen tarkemmassa näkymässä painamalla Tallenna nappia lisätään tavoite listaan
     * tehdyttavoitteet ja poistetaan se alkuperäisestä listasta.
     * @param v PäivänTavoitteet näkymä
     */
    public void merkkaaValmiiks(View v){
        //Etsii sijainnin mukaan tavoitteen ja poistaa sen, koska se on valmis
        TehdytTavoitteet.getInstance().lisaaTehdyt(this);
        Tavoitteet.getInstance().poistha(this, sijainti);
        adapter.notifyDataSetChanged();
        popup.cancel();
    }

    /**
     * Käyttäjä voi poistaa tavoitteen,jos on esimerkiksi kirjoittanut jotain väärin
     * @param v
     */
    public void poistaTavoite(View v){
        Tavoitteet.getInstance().poistha(this, sijainti);
        adapter.notifyDataSetChanged();
        popup.cancel();
    }
    /**
     * Funktio aktivoituu napin painalluksella ja avaa näkymän, josta käyttäjä pääsee lisäämään tavoitteen.
     * @param v PäivänTavoitteet näkymä
     */
    public void lisaaTavoite(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        popupView = getLayoutInflater().inflate(R.layout.alert_tavoite, null);
        builder.setView(popupView);
        popup = builder.create();
        popup.show();
    }
    /**
     * Haetaan tiedot lisaaTavoite() komennon avaamasta näkymästä ja lisätään tämä tavoite --> tavoitteet listaan
     * @param v PäivänTavoitteet näkymä
     */
    public void tallennaTavoite(View v){
        String tavoite = ((EditText) popupView.findViewById(R.id.tavoite)).getText().toString();

        //Jos kenttä on tyhjä
        if(tavoite.equals("")){
            Toast.makeText(this, "Täytä kaikki kentät", Toast.LENGTH_LONG).show();
        }else {
            Tavoite tv = new Tavoite(tavoite);
            Tavoitteet.getInstance().lisaaTavoite(tv, this);
            adapter.notifyDataSetChanged();
            popup.cancel();
        }
    }
}