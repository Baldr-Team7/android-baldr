package com.gu.example.axel.baldr;

/**
 * Created by Arasb on 2016-11-21.
 *
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class LaunchLogo extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 300000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                Intent i = new Intent(LaunchLogo.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}