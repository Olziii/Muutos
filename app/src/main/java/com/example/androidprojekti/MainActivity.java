package com.example.androidprojekti;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

/**
 *
 * Luokka, joka ohjaa koko sovelluksen toimintaa ja vie käyttäjää seuraaviin näkymiin
 * @author Oliver Hamberg
 * @author Joona Nylander
 * @author Niklas Kukkonen
 * @since  12.11.2020
 */
public class MainActivity extends AppCompatActivity {
    private Gson gson = new Gson();
    private View popup;
    private Integer keskiverokulutusMies;
    private Integer keskiverokulutusNainen;
    private TextView kulutus, tilanne;
    private static boolean kaynnistys = true;

    ProgressBar progress;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ladataan aiemmin tallennetut tiedot SharedPreferenceistä
        Ateriat.getInstance().alusta(this);
        Tavoitteet.getInstance().alusta(this);
        UrheiluSuoritukset.getInstance().alusta(this);
        TehdytTavoitteet.getInstance().alusta(this);
        ProfileHolder.getInstance().alusta(this);

        // Asetetaan normaali näkymä ja laitetaan näkyviin päivämäärä
        setContentView(R.layout.activity_main);
        TextView paiva = findViewById(R.id.dateView);
        paiva.setText(Aika.getAika());

        //Kun onCreate methodia kutsutaan ensimmäistä kertaa sovelluksen käynnistyksessä,
        //näytetään "Avausruutu", joka kestää 3 sekuntia
        if(kaynnistys) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
            View popupView = getLayoutInflater().inflate(R.layout.alert_sisaantulo, null);
            builder.setView(popupView);
            AlertDialog popup = builder.create();
            popup.show();
            TextView nimi = popup.findViewById(R.id.nimi);
            if (ProfileHolder.getInstance().getProfile().getNimi().equalsIgnoreCase("NULL")) {
                nimi.setText("Muutos alkaa tästä!");
            } else {
                nimi.setText("Tervetuloa takaisin " + ProfileHolder.getInstance().getProfile().getNimi() + "!");
            }
            ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
            ses.schedule((Runnable) () -> {
                runOnUiThread(popup::cancel);
            }, 3, TimeUnit.SECONDS);
        }

        //Käynnistys on nyt tehty
        kaynnistys = false;

        //Haetaan eiliseltä tiedostojen päivämäärä, jos ne ovat nyt muuttuneet,
        //tyhjennetään listat, jotta "Päivän tavoitteet" jne. ei näy seuraavana päivänä.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int vuodenpaiva = prefs.getInt("vuodenpäivä", Aika.getVuodenPaiva());
        if(vuodenpaiva != Aika.getVuodenPaiva()){
            Ateriat.getInstance().reset(this);
            Tavoitteet.getInstance().reset(this);
            TehdytTavoitteet.getInstance().reset(this);
            UrheiluSuoritukset.getInstance().reset(this);
        }

        //Asetetaan aloitusruudun arvot
        kulutus = findViewById(R.id.kulutus);
        progress = findViewById(R.id.progressBar);
        progress.setMax(Tavoitteet.getInstance().getTavoitteet().size() + TehdytTavoitteet.getInstance().getTehdyt());
        progress.setProgress(TehdytTavoitteet.getInstance().getTehdyt());
        tilanne = findViewById(R.id.tilanne);
        tilanne.setText(TehdytTavoitteet.getInstance().getTehdyt() + " / " + (Tavoitteet.getInstance().getTavoitteet().size() + TehdytTavoitteet.getInstance().getTehdyt()) + "\ntavoitetta tehty");
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        //Kun näkymään palataan, päivitetään aloitusruudun arvot
        keskiverokulutusMies = (int) Math.round(66 + (6.2 * ProfileHolder.getInstance().getProfile().getPaino()) + (12.7 * 171) - (6.76 * ProfileHolder.getInstance().getProfile().getIka()));
        keskiverokulutusNainen = (int) Math.round(655.1 + (4.35 * ProfileHolder.getInstance().getProfile().getPaino()) + (4.7 * 171) - (4.7 * ProfileHolder.getInstance().getProfile().getIka()));
        if (kulutus != null) {
            if(ProfileHolder.getInstance().getProfile().getSukupuoli().equalsIgnoreCase("Nainen")){
                String ateriakalorit = Ateriat.getInstance().getKalorit().toString();
                String urheilukalorit = UrheiluSuoritukset.getInstance().getKalorit().toString();
                int summa = (Ateriat.getInstance().getKalorit() - UrheiluSuoritukset.getInstance().getKalorit() - keskiverokulutusNainen);
                if(summa < 0){
                    kulutus.setText("Kaloreita syöty: " + ateriakalorit + " kcal\nKaloreita kulutettu urheilemalla: " + urheilukalorit + "\nKaloreita kulutettu passiivisesti: " + keskiverokulutusNainen.toString() +
                            "\nKalorikulutus tänään: " + summa+"");
                }else {
                    kulutus.setText("Kaloreita syöty: " + ateriakalorit + " kcal\nKaloreita kulutettu urheilemalla: " + urheilukalorit + "\nKaloreita kulutettu passiivisesti: " + keskiverokulutusNainen.toString() +
                            "\nSöit tänään ylimääräisiä kaloreita: " + abs(summa) + "");
                }
            }else if(ProfileHolder.getInstance().getProfile().getSukupuoli().equalsIgnoreCase("Mies") || ProfileHolder.getInstance().getProfile().getSukupuoli().equalsIgnoreCase("Muu")){
                String ateriakalorit = Ateriat.getInstance().getKalorit().toString();
                String urheilukalorit = UrheiluSuoritukset.getInstance().getKalorit().toString();
                int summa = (Ateriat.getInstance().getKalorit() - UrheiluSuoritukset.getInstance().getKalorit() - keskiverokulutusMies);
                if(summa < 0) {
                    kulutus.setText("Kaloreita syöty: " + ateriakalorit + " kcal\nKaloreita kulutettu urheilemalla: " + urheilukalorit + "\nKaloreita kulutettu passiivisesti\npäivän lopussa: " + keskiverokulutusMies.toString() +
                            "\nKalorikulutus tänään päivän lopuksi: " + abs(summa) + "");
                }else{
                    kulutus.setText("Kaloreita syöty: " + ateriakalorit + " kcal\nKaloreita kulutettu urheilemalla: " + urheilukalorit + "\nKaloreita kulutettu passiivisesti\npäivän lopussa: " + keskiverokulutusMies.toString() +
                            "\nSöit tänään ylimääräisiä kaloreita: " + summa + "");
                }
            }else{
                //Jos profiilia ei vielä ole
                kulutus.setText("Tee profiili asetuksissa!");
            }
        }
    }


    /**
     * Vaihtaa aktiviteettia "Ruoat" näkymään
     * @param v Aloitus näkymä
     */
    public void paivanRuoat(View v){
        Intent ruoat = new Intent(this, PaivanRuoat.class);
        startActivity(ruoat);
    }
    /**
     * Vaihtaa aktiviteettia "Urheilusuoritukset" näkymään
     * @param v Aloitus näkymä
     */
    public void paivanUrheilu(View v){
        Intent urheilu = new Intent(this, PaivanUrheilu.class);
        startActivity(urheilu);
    }
    /**
     * Vaihtaa aktiviteettia "Tavoitteet" näkymään
     * @param v Aloitus näkymä
     */
    public void kaikkiTavoitteet(View v){
        Intent tavoitteet = new Intent(this, PaivanTavoitteet.class);
        startActivity(tavoitteet);
    }
    /**
     * Vaihtaa aktiviteettia "Asetukset" näkymään
     * @param v Aloitus näkymä
     */
    public void kaikkiAsetukset(View v){
        Intent asetukset = new Intent(this, Asetukset.class);
        startActivity(asetukset);
    }
}