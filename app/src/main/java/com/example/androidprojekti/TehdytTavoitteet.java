package com.example.androidprojekti;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Luokka, jonka toiminnot käsittelevät tehtyjen tavoitteiden määrää
 * @author Oliver Hamberg
 * @author Joona Nylander
 * @author Niklas Kukkonen
 * @since  11.12.2020
 */
public class TehdytTavoitteet {
    private static final TehdytTavoitteet instance = new TehdytTavoitteet();
    private int tehdyt = 0;
    SharedPreferences prefs;

    /**
     * Saadaan haettua aina sama TehdyTavoitteet instanssi
     * @return
     */
    public static TehdytTavoitteet getInstance() {
        return instance;
    }

    /**
     * Lisätään tehtyjen määrään 1
     * @param context
     */
    public void lisaaTehdyt(Context context){
        tehdyt += 1;
        tallennaTehdyt(context);
    }

    /**
     * Päivän vaihtuessa kutsuttava methodi, joka nollaa tehdyt
     * @param context
     */
    public void reset(Context context) {
        this.tehdyt = 0;
        tallennaTehdyt(context);
    }

    public int getTehdyt() {
        return tehdyt;
    }

    /**
     * Tallentaa tehtyjen määrän SharedPreferenceihin
     * @param context
     */
    public void tallennaTehdyt(Context context){
        prefs.edit().putInt("tehdyt", tehdyt).commit();
    }
    public void alusta(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        tehdyt = prefs.getInt("tehdyt", 0);
    }
}
