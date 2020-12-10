package com.example.androidprojekti;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Luokka, jossa käytetään Profiili-olioon liittyviä toimintoja, kuten tallennus
 * @author Oliver Hamberg
 * @author Joona Nylander
 * @author Niklas Kukkonen
 * @since  12.11.2020
 */
public class ProfileHolder {
    private static ProfileHolder instance = new ProfileHolder();
    private static Profiili profile = new Profiili();
    private Gson gson = new Gson();

    public static ProfileHolder getInstance() {
        return instance;
    }

    public Profiili getProfile() {
        return profile;
    }

    public void alusta(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString("Profiili", null);
        if (json != null) {
            profile = gson.fromJson(json, Profiili.class);
        }
    }
    public void tallenna(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("Profiili", gson.toJson(profile)).commit();
    }
}
