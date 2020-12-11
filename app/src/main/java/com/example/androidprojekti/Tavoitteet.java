package com.example.androidprojekti;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tavoite-olioita varten tehty lista ja sen toiminnot
 * @author Oliver Hamberg
 * @author Joona Nylander
 * @author Niklas Kukkonen
 * @since  11.12.2020
 */
public class Tavoitteet {
    Gson gson = new Gson();
    SharedPreferences prefs;
    private List<Tavoite> tavoitteet;
    private static final Tavoitteet instance = new Tavoitteet();
    /**
     * Palauttaan Tavoitteet instanssin, jotta voimme muuttaa juuri tätä tavoitteet listaa.
     * @return
     */
    public static Tavoitteet getInstance(){
        return instance;
    }
    /**
     * Luo uuden listan tavoitteille
     */
    private Tavoitteet(){
        tavoitteet = new ArrayList<>();
    }
    /**
     * Lisätään Tavoite-olioita tavoitteet listaan
     * @param tavoite
     * @param context
     */
    public void lisaaTavoite(Tavoite tavoite, Context context){
        tavoitteet.add(tavoite);
        tallenna(context);
    }
    /**
     * Tallennetaan tiedot SharedPreferenceihin muuntamalla oliot json muotoon.
     * @param context
     */
    public void tallenna(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> jsonit = new HashSet<>();
        for (Tavoite tavoite : tavoitteet) {
            jsonit.add(gson.toJson(tavoite));
        }
        prefs.edit().putStringSet("tavoitteet", jsonit).commit();
    }
    /**
     * Haetaan aina sovelluksen käynnistäessä ateriat listan tiedot SharedPreferenceistä ja muunnetaan ne olioiksi.
     * @param context
     */
    public void alusta(Context context){
        tavoitteet.clear();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        for(String json: prefs.getStringSet("tavoitteet", new HashSet<>())){
            tavoitteet.add(gson.fromJson(json, Tavoite.class));
        }
    }

    /**
     * Poistetaan tavoitteet listasta yksi tavoite, tietystä kohtaa
     * @param context
     * @param index sijainti
     */
    public void poistha(Context context, int index){
        tavoitteet.remove(index);
        tallenna(context);
    }
    public List<Tavoite> getTavoitteet(){
        return tavoitteet;
    }

    /**
     * Päivän vaihtuessa kutsuttava methodi, joka tyhjentää listan
     * @param context
     */
    public void reset(Context context){
        tavoitteet.clear();
        tallenna(context);
    }
}
