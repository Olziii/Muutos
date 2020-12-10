package com.example.androidprojekti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.w3c.dom.Text;
/**
 * Luokka, joka sisältää Asetukset näkymän toiminnot
 * @author Oliver Hamberg
 * @author Joona Nylander
 * @author Niklas Kukkonen
 * @since  12.11.2020
 */
public class Asetukset extends AppCompatActivity {
    private static final String TAG = "kaikkiAsetukset";
    Profiili kayttajaprofiili;
    SharedPreferences prefs;
    private Gson gson = new Gson();
    private View popupView;
    private AlertDialog popup;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaikki_asetukset);
        Intent intent = getIntent();
        kayttajaprofiili = ProfileHolder.getInstance().getProfile();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("kayttaja", kayttajaprofiili.getNimi());
    }

    /**
     * Funktio aktivoituu napin painalluksella ja avaa näkymän, jossa voi lisätä profiilin tai katsoa omaa profiilia.
     * @param v Asetukset näkymä
     */
    public void naytaProfiili(View v){

        //Jos profiilia ei vielä ole
        if(kayttajaprofiili.getNimi().equalsIgnoreCase("NULL")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            popupView = getLayoutInflater().inflate(R.layout.alert_profiili, null);
            builder.setView(popupView);
            popup = builder.create();
            popup.show();
        }else{
            //Jos profiili on jo tehty asetetaan näkyviin profiilin arvot
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            popupView = getLayoutInflater().inflate(R.layout.alert_valmis_profiili, null);
            builder.setView(popupView);
            popup = builder.create();
            popup.show();
            TextView nimi = popup.findViewById(R.id.pNimi);
            TextView ika = popup.findViewById(R.id.pIka);
            TextView paino = popup.findViewById(R.id.pPaino);
            TextView sukupuoli = popup.findViewById(R.id.pSukupuoli);
            TextView rasitustaso = popup.findViewById(R.id.pRasitusTaso);
            nimi.setText(kayttajaprofiili.getNimi());
            ika.setText(kayttajaprofiili.getIka().toString());
            paino.setText(kayttajaprofiili.getPaino().toString());
            sukupuoli.setText(kayttajaprofiili.getSukupuoli());
            rasitustaso.setText(kayttajaprofiili.getRasitustaso().toString());
        }
    }
    /**
     * Haetaan tiedot naytaProfiili() komennon avaamasta näkymästä ja annetaan tämä profiili --> profiiliholder oliolle, joka tallentaa profiilin.
     * @param v Asetukset näkymä
     */
    public void tallennaProfiili(View v){
        String nimi = ((EditText) popupView.findViewById(R.id.pNimi)).getText().toString();
        String ika = ((EditText) popupView.findViewById(R.id.pIka)).getText().toString();
        String paino = ((EditText) popupView.findViewById(R.id.pPaino)).getText().toString();
        String sukupuoli = haeValittu();
        String rasitustaso = ((EditText) popup.findViewById(R.id.rasitusTaso)).getText().toString();

        //Jos joku kenttä on tyhjä
        if(nimi.equals("") || ika.equals("") || paino.equals("") || sukupuoli.equals("")){
            Toast.makeText(this, "Täytä kaikki kentät", Toast.LENGTH_LONG).show();
        }else {
            try {
                kayttajaprofiili.setNimi(nimi);
                kayttajaprofiili.setIka(Integer.parseInt(ika));
                kayttajaprofiili.setPaino(Integer.parseInt(paino));
                kayttajaprofiili.setSukupuoli(sukupuoli);
                kayttajaprofiili.setRasitustaso(Integer.parseInt(rasitustaso));
                popup.cancel();
            }
            catch (Exception e){
                //Jos vain lukuja vastaanottavia kenttiä on täytetty tekstillä
                Toast.makeText(this, "Täytä kaikki kentät! Huomioi, että ikä-, paino- ja rasitustaso-kentät ottavat vastaan vain lukuja!", Toast.LENGTH_LONG).show();
            }
        }
        ProfileHolder.getInstance().tallenna(this);
    }
    /**
     * Hakee naytaProfiili() komennon avaamasta näkymästä tiedon sukupuolesta
     */
    public String haeValittu(){
        id = ((RadioGroup) popupView.findViewById(R.id.buttonGroupa)).getCheckedRadioButtonId();
        if (id == R.id.buttonMies) {
            return "Mies";
        }else if(id == R.id.buttonNainen){
            return "Nainen";
        }else{
            return "Muu";
        }
    }
    /**
     * Vaihtaa aktiviteettia "Tietosuoja" näkymään
     * @param v Asetukset näkymä
     */
    public void avaaTietosuoja(View v){
        Intent tietosuoja = new Intent(this, Tietosuoja.class);
        startActivity(tietosuoja);
    }
    /**
     * Vaihtaa aktiviteettia "Yhteystiedot näkymään
     * @param v Asetukset näkymä
     */
    public void avaaYhteystiedot(View v){
        Intent yhteystiedot = new Intent(this, Yhteystiedot.class);
        startActivity(yhteystiedot);
    }
}