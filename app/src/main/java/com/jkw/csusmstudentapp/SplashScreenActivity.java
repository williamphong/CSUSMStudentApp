package com.jkw.csusmstudentapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.jkw.csusmstudentapp.ui.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            if(false){
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            }
            else{
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            }

            finish();
        }, 2000);
    }
}