package com.jkw.csusmstudentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

import com.jkw.csusmstudentapp.Login.Create;
import com.jkw.csusmstudentapp.data.LoginRepository;
import com.jkw.csusmstudentapp.ui.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(false){
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
                else{
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                }

                finish();
            }
        }, 2000);
    }
}