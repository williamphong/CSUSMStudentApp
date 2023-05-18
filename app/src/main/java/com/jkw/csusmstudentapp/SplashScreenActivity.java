package com.jkw.csusmstudentapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.jkw.csusmstudentapp.Parking.ParkingPage;
import com.jkw.csusmstudentapp.dbConnector.parkingAccessor;
import com.jkw.csusmstudentapp.ui.login.LoginActivity;

import java.sql.SQLException;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new DatabaseTask().execute();
        new Handler().postDelayed(() -> {
            //TODO: add a user logged in check
            if(false){
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            }
            else{
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            }

            finish();
        }, 2500);
    }

    static class DatabaseTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            parkingAccessor pa;
            try {
                pa = new parkingAccessor();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String[] arr;
            try {
                arr = pa.getArr();
                ParkingPage.parkArr = arr;
                Log.d("MY MESSAGE!!", "PARKING");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return null;
        }

    }
}