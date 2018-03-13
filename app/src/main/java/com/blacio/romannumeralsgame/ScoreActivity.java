package com.blacio.romannumeralsgame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.AlertDialog;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.String;

public class ScoreActivity extends Activity {

    TextView finaly;
    EditText lastPlayer;
    Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        finaly = (TextView) findViewById(R.id.finalPlayer);
        lastPlayer = (EditText) findViewById(R.id.nameLastPlayer);

        Intent intent = getIntent();
        String message = intent.getStringExtra(GameActivity.EXTRA_MESSAGE);

        finaly.setText(message);


        SharedPreferences sharedPrefr = getSharedPreferences("userInfo",Context.MODE_PRIVATE);

        lastPlayer.setText(sharedPrefr.getString("lastPlayer",""));
        lastPlayer.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        finish = (Button) findViewById(R.id.readyButton);

        finish.setOnClickListener(new View.OnClickListener() {

                                      public void onClick(View view) {

                                          ConnectivityManager cm =(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                                          NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                                          boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                                          if(!isConnected){
                                              create_dialog().show();
                                          }

                                          else {

                                              SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                                              if (!lastPlayer.getText().toString().equals("")) {

                                                  SharedPreferences.Editor editor = sharedPref.edit();
                                                  editor.putString("lastPlayer", lastPlayer.getText().toString());
                                                  editor.apply();

                                                  addSortedPlayer(lastPlayer.getText().toString(), finaly.getText().toString());


                                              } else {
                                                  SharedPreferences.Editor editor = sharedPref.edit();
                                                  editor.putString("lastPlayer", "");
                                                  editor.apply();

                                                  addSortedPlayer("NoName", finaly.getText().toString());

                                              }
                                                startIntent();
                                          }
                                      }
                                  }
        );
    }


    public void startIntent(){
        Intent i = new Intent(ScoreActivity.this, MenuActivity.class);
        startActivity(i);
    }

    public void addSortedPlayer(String jucator,String rezultat){

        SharedPreferences sharedPrefer = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefer.edit();


        if (Long.parseLong(rezultat) > Long.parseLong(sharedPrefer.getString("rezultat", "0"))) {
            editor.putString("jucator", jucator);
            editor.putString("rezultat", rezultat);
            editor.apply();

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startIntent();
        }
        return super.onKeyDown(keyCode, event);
    }

    Dialog create_dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.box_menu);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Continue without saving", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startIntent();
            }
        });
        return builder.create();
    }

}
