package com.blacio.romannumeralsgame;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;


public class GameActivity extends Activity {

    AudioManager audio;
    InterstitialAd InterAd;
    public final static String EXTRA_MESSAGE = "MESAJ";

    MyCountDownTimer count = new MyCountDownTimer(10000, 1000);
    MyCountDownTimer count2 = new MyCountDownTimer(7000, 1000);
    MyCountDownTimer count3 = new MyCountDownTimer(5000, 1000);
    Random rand;
    TextView nb, sec, scor;
    EditText rez;
    Button but, sw;
    int rezt, finish = 0;
    long score = 0;
    String numbers = "^[0-9]*$";
    MediaPlayer m1,m2,m3,m4;
    long level;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        try {
            InterAd = new InterstitialAd(this);
            InterAd.setAdUnitId("***");
            AdRequest request2 = new AdRequest.Builder()
                    .build();

            InterAd.loadAd(request2);
        }catch(Exception e){}

        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        level  = 1;

        nb = (TextView) findViewById(R.id.nr);
        rez = (EditText) findViewById(R.id.result);
        but = (Button) findViewById(R.id.button);
        sec = (TextView) findViewById(R.id.sec);
        scor = (TextView) findViewById(R.id.score);
        sw = (Button) findViewById(R.id.change);


        m1 = MediaPlayer.create(this, R.raw.correct);
        m2 = MediaPlayer.create(this, R.raw.clock_down);
        m3 = MediaPlayer.create(this, R.raw.clock_up);
        m4 = MediaPlayer.create(this,R.raw.wrong);

        update();

        but.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (rez.getText().toString().matches(""))
                    ;
                else {

                    if(level<=33)
                        count.cancel();
                    else if(level<66 && level>33)
                        count2.cancel();
                    else
                        count3.cancel();

                    if (rez.getText().toString().trim().matches(numbers)) {

                        try {
                            if (rezt == Integer.parseInt(rez.getText().toString())) {

                                if (audio.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
                                    m1.start();

                                if (level <= 33)
                                    count.onCorrect();
                                else if (level < 66 && level > 33)
                                    count2.onCorrect();
                                else
                                    count3.onCorrect();

                                level++;

                                update();

                            } else {
                                finish = 1;
                                update();
                            }
                        }catch (Exception e){
                            Toast.makeText(GameActivity.this, "This number is too large",
                                Toast.LENGTH_SHORT).show();}
                    }

                    else {
                        finish = 1;
                        update();
                    }
                }
            }
        });

        sw.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if(level<=33)
                    count.cancel();
                else if(level<66 && level>33)
                    count2.cancel();
                else
                    count3.cancel();

                if(level<=33)
                    count.onSwitch();
                else if(level<66 && level>33)
                    count2.onSwitch();
                else
                    count3.onSwitch();

                if (score < 0) {
                    score = 0;
                    finish = 1;
                    update();
                }

                else {
                    if(audio.getRingerMode()==AudioManager.RINGER_MODE_NORMAL)
                        m3.start();

                    update();
                }
            }
        });

        try {
            InterAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    startScoreIntent();
                }
            });
        }catch(Exception e){}
    }

    private int randomNr() {

        rand = new Random();
        return rand.nextInt(999) + 1;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(level<=33)
            count.cancel();
        else if(level<66 && level>33)
            count2.cancel();
        else
            count3.cancel();
    }

    private String generateRomanFirst(int nr) {

        int n = nr;
        n = n % 10;

        String numar = "";

        switch (n) {
            case 0:
                break;
            case 1:
                numar = "I";
                break;
            case 2:
                numar = "II";
                break;
            case 3:
                numar = "III";
                break;
            case 4:
                numar = "IV";
                break;
            case 5:
                numar = "V";
                break;
            case 6:
                numar = "VI";
                break;
            case 7:
                numar = "VII";
                break;
            case 8:
                numar = "VIII";
                break;
            case 9:
                numar = "IX";
                break;
        }
        return numar;
    }

    private String generateRomanSecond(int nr) {

        int n = nr;
        n = n % 100;
        n = n / 10;

        String numar = "";

        switch (n) {
            case 0:
                break;
            case 1:
                numar = "X";
                break;
            case 2:
                numar = "XX";
                break;
            case 3:
                numar = "XXX";
                break;
            case 4:
                numar = "XL";
                break;
            case 5:
                numar = "L";
                break;
            case 6:
                numar = "LX";
                break;
            case 7:
                numar = "LXX";
                break;
            case 8:
                numar = "LXXX";
                break;
            case 9:
                numar = "XC";
                break;
        }
        return numar;
    }

    private String generateRomanThird(int nr) {

        int n = nr;
        n = n / 100;

        String numar = "";

        switch (n) {
            case 0:
                break;
            case 1:
                numar = "C";
                break;
            case 2:
                numar = "CC";
                break;
            case 3:
                numar = "CCC";
                break;
            case 4:
                numar = "CD";
                break;
            case 5:
                numar = "D";
                break;
            case 6:
                numar = "DC";
                break;
            case 7:
                numar = "DCC";
                break;
            case 8:
                numar = "DCCC";
                break;
            case 9:
                numar = "CM";
                break;
        }
        return numar;
    }


    public void update() {

        if(level<=33)
            count.cancel();
        else if(level<66 && level>33)
            count2.cancel();
        else
            count3.cancel();

        if (finish == 0) {

            rezt = randomNr();
            String sir;

            if (rezt < 10)
                sir = generateRomanFirst(rezt);
            else if (rezt >= 10 && rezt < 100)
                sir = generateRomanSecond(rezt) + generateRomanFirst(rezt);
            else
                sir = generateRomanThird(rezt) + generateRomanSecond(rezt) + generateRomanFirst(rezt);


            nb.setText(sir);

            rez.setText("");

            if(level<=33)
                count.start();
            else if(level<66 && level>33)
                count2.start();
            else
                count3.start();
        } else {

            if(audio.getRingerMode()==AudioManager.RINGER_MODE_NORMAL)
                m4.start();

            show_interst();

        }
    }

    public class MyCountDownTimer extends CountDownTimer {

        long timeLeft;

        private MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timeLeft = millisUntilFinished;
            sec.setText("" + millisUntilFinished / 1000);

            if (millisUntilFinished / 1000 > 3){
                if(audio.getRingerMode()==AudioManager.RINGER_MODE_NORMAL)
                    m2.start();}
            else {
                if (audio.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
                    m3.start();
            }

        }

        @Override
        public void onFinish() {

            if(finish==1)
                ;
            else {
                sec.setText("");
                finish = 1;
                update();
            }
        }

        private void onCorrect() {
            score += timeLeft / 1000;
            scor.setText("Score: " + score);
        }

        private void onSwitch() {
            score -= 10;

            if (score >= 0)
                scor.setText("Score: " + score);
            else
                scor.setText("Score: " + 0);
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if(level<=33)
            count.cancel();
        else if(level<66 && level>33)
            count2.cancel();
        else
            count3.cancel();
        finish = 1;
        update();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(GameActivity.this, MenuActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void show_interst(){
        if(InterAd.isLoaded())
            InterAd.show();
        else startScoreIntent();
    }

    private void startScoreIntent(){
        Intent intent = new Intent(GameActivity.this, ScoreActivity.class);
        String str = Long.toString(score);
        score=0;
        intent.putExtra(EXTRA_MESSAGE, str);
        startActivity(intent);
    }
}