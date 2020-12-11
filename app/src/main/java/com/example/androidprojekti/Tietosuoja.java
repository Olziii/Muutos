package com.example.androidprojekti;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Luokka, joka laittaa esille tietosuoja ja käyttöehto selosteen Asetukset näkymässä
 * @author Oliver Hamberg
 * @author Joona Nylander
 * @author Niklas Kukkonen
 * @since  11.12.2020
 */
public class Tietosuoja extends AppCompatActivity {
    TextView seloste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tietosuoja_ja_kayttoehdot);
        seloste = findViewById(R.id.suojaehdot);
        seloste.setMovementMethod(new ScrollingMovementMethod());
    }
}
