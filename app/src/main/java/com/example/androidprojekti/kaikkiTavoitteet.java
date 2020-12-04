package com.example.androidprojekti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class kaikkiTavoitteet extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaikki_tavoitteet);
        TextView paivamaara = (TextView) findViewById(R.id.dateView);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date aika = new Date();
        paivamaara.setText(formatter.format(aika));
        Intent intent = getIntent();
    }
    public void viikonTavoite(View v){
        Intent viikonTavoite = new Intent(this, viikonTavoite.class);
        startActivity(viikonTavoite);
    }
    public void kuukaudenTavoite(View v){
        Intent kuukaudenTavoite = new Intent(this, kuukaudenTavoite.class);
        startActivity(kuukaudenTavoite);
    }
    public void vuodenTavoite(View v){
        Intent vuodenTavoite = new Intent(this, vuodenTavoite.class);
        startActivity(vuodenTavoite);
    }
}