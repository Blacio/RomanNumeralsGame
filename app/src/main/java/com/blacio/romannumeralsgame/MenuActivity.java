package com.blacio.romannumeralsgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MenuActivity extends Activity {

    Button but1,but2,but3;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        but1 = (Button) findViewById(R.id.startButton);
        but2 = (Button) findViewById(R.id.bestButton);
        but3 = (Button) findViewById(R.id.instructButton);

    }

    public void startClicked(View view) {

        Intent i = new Intent(MenuActivity.this, GameActivity.class);
        startActivity(i);
    }

    public void bestClicked(View view){

        Intent i = new Intent(MenuActivity.this, HighScoreActivity.class);
        startActivity(i);

    }

    public void intructClicked(View view){

        Intent i = new Intent(MenuActivity.this, InstructActivity.class);
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
    }
}
