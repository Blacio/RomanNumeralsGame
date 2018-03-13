package com.blacio.romannumeralsgame;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HighScoreActivity extends Activity{

    String sir1,sir2;
    TextView nume,rez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nume = (TextView) findViewById(R.id.finalPlayer);
        rez = (TextView) findViewById(R.id.finalScore);

        SharedPreferences sharedPrefr = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        sir1 = sharedPrefr.getString("jucator", "Nobody");
        sir2 = sharedPrefr.getString("rezultat", "0");

        nume.setText(sir1);
        rez.setText(sir2);


        try {
            AdView adview = (AdView) findViewById(R.id.adview);

            AdRequest request = new AdRequest.Builder()
                    .build();

            adview.loadAd(request);
        }catch(Exception e){}

    }

}
