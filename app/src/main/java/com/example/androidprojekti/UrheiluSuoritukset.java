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
 * UrheiluSuoritus-olioita varten tehty lista ja sen toiminnot
 */
public class UrheiluSuoritukset {
    Gson gson = new Gson();
    SharedPreferences prefs;
    private List<UrheiluSuoritus> urheilusuoritukset;
    private static final UrheiluSuoritukset instance = new UrheiluSuoritukset();
    /**
     * Palauttaan UrheiluSuoritukset instanssin, jotta voimme muuttaa juuri tätä urheilusuoritukset listaa.
     * @return
     */
    public static UrheiluSuoritukset getInstance(){
        return instance;
    }

    /**
     * Luodaan uusi lista UrheiluSuoritus olioille
     */
    private UrheiluSuoritukset(){
        urheilusuoritukset = new ArrayList<>();
    }
    /**
     * Lisätään UrheiluSuoritus olioita urheilusuoritukset listaan
     * @param  urheilusuoritus
     * @param context
     */
    public void lisaaSuoritus(UrheiluSuoritus urheilusuoritus, Context context){
        urheilusuoritukset.add(urheilusuoritus);
        tallenna(context);
    }

    /**
     * Tallennetaan tiedot SharedPreferenceihin muuntamalla oliot json muotoon.
     * @param context
     */
    public void tallenna(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> jsonit = new HashSet<>();
        for (UrheiluSuoritus urheilusuoritus: urheilusuoritukset) {
            jsonit.add(gson.toJson(urheilusuoritus));
        }
        prefs.edit().putStringSet("urheilusuoritukset", jsonit).commit();
    }
    /**
     * Haetaan aina sovelluksen käynnistäessä urheilusuoritukset listan tiedot SharedPreferenceistä ja muunnetaan ne olioiksi.
     * @param context
     */
    public void alusta(Context context){
        urheilusuoritukset.clear();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        for(String json: prefs.getStringSet("urheilusuoritukset", new HashSet<>())){
            urheilusuoritukset.add(gson.fromJson(json, UrheiluSuoritus.class));
        }
    }/**
     * Palauttaa urheilusuorituksen takia kuluneen kokonaiskalorimäärän
     * @return
     */
    public Integer getKalorit() {
        int summa = 0;
        for (UrheiluSuoritus urheiluSuoritus : urheilusuoritukset) {
            summa += urheiluSuoritus.getKalorikulutus();
        }
        return summa;
    }
    public List<UrheiluSuoritus> getSuoritukset(){
        return urheilusuoritukset;
    }
    /**
     * Päivän vaihtuessa kutsuttava methodi, joka tyhjentää listan
     * @param context
     */
    public void reset(Context context){
        urheilusuoritukset.clear();
        tallenna(context);
    }
}

