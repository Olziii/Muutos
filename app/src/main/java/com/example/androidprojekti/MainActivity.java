package com.example.androidprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView paivamaara = (TextView) findViewById(R.id.dateView);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date aika = new Date();
        paivamaara.setText(formatter.format(aika));
    }
    public void paivanRuoat(View v){
        Intent ruoat = new Intent(this, paivanRuoat.class);
        startActivity(ruoat);
    }
    public void paivanUrheilu(View v){
        Intent urheilu = new Intent(this, paivanUrheilu.class);
        startActivity(urheilu);
    }
    public void kaikkiTavoitteet(View v){
        Intent tavoitteet = new Intent(this, kaikkiTavoitteet.class);
        startActivity(tavoitteet);
    }
    public void kaikkiAsetukset(View v){
        Intent asetukset = new Intent(this, kaikkiAsetukset.class);
        startActivity(asetukset);
    }
}