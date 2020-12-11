package com.example.androidprojekti;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Ateria-oliota varten tehty lista ja sen toiminnot
 * @author Oliver Hamberg
 * @author Joona Nylander
 * @author Niklas Kukkonen
 * @since  11.12.2020
 */
public class Ateriat {
    Gson gson = new Gson();
    SharedPreferences prefs;
    private List<Ateria> ateriat;
    private static final Ateriat instance = new Ateriat();
    /**
     * Palauttaan Ateriat instanssin, jotta voimme muuttaa juuri tätä ateriat listaa.
     * @return
     */
    public static Ateriat getInstance(){
        return instance;
    }

    /**
     * Luo uuden listan aterioille
     */
    private Ateriat(){
        ateriat = new ArrayList<>();
    }

    /**
     * Lisätään Ateria-olioita ateriat listaan
     * @param ateria
     * @param context
     */
    public void lisaaAteria(Ateria ateria, Context context){
        ateriat.add(ateria);
        tallenna(context);
    }

    /**
     * Tallennetaan tiedot SharedPreferenceihin muuntamalla oliot json muotoon.
     * @param context
     */
    public void tallenna(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> jsonit = new HashSet<>();
        for (Ateria ateria : ateriat) {
            //Muutetaan olio --> json muotoon
            jsonit.add(gson.toJson(ateria));
        }
        prefs.edit().putStringSet("ateriat", jsonit).commit();
    }

    /**
     * Haetaan aina sovelluksen käynnistäessä ateriat listan tiedot SharedPreferenceistä ja muunnetaan ne olioiksi.
     * @param context
     */
    public void alusta(Context context){
        ateriat.clear();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        for(String json: prefs.getStringSet("ateriat", new HashSet<>())){
            //Muutetaan json --> olioksi
            ateriat.add(gson.fromJson(json, Ateria.class));
        }
    }
    public List<Ateria> getAteriat(){
        return ateriat;
    }

    /**
     * Palauttaa aterioiden kokonaiskalorimäärän
     * @return
     */
    public Integer getKalorit(){
        int summa = 0;
        for(Ateria ateria: ateriat){
            summa += ateria.getAteriankalorit() + ateria.getJuomankalorit();
        }
        return summa;
    }

    /**
     * Päivän vaihtuessa kutsuttava methodi, joka tyhjentää listan
     * @param context
     */
    public void reset(Context context){
        ateriat.clear();
        tallenna(context);
    }
}

