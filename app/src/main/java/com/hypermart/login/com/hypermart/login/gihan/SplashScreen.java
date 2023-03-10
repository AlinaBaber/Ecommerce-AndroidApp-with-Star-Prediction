package com.hypermart.login.com.hypermart.login.gihan;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.helani.SignIn;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gihan_activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash = new Intent(SplashScreen.this, SignIn.class);
                startActivity(splash);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
