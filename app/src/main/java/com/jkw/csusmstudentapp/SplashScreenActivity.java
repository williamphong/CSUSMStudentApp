package com.jkw.csusmstudentapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.jkw.csusmstudentapp.Calendar.Event;
import com.jkw.csusmstudentapp.Parking.ParkingPage;
import com.jkw.csusmstudentapp.dbConnector.assignmentAccessor;
import com.jkw.csusmstudentapp.dbConnector.parkingAccessor;
import com.jkw.csusmstudentapp.ui.login.LoginActivity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new CalendarTask().execute();
        new ParkingTask().execute();

        new Handler().postDelayed(() -> {
            //TODO: add a user logged in check
            if (false) {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            }

            finish();
        }, 2500);
    }

    static class CalendarTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            assignmentAccessor aa;
            try {
                aa = new assignmentAccessor();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Integer> list;
            try {
                list = aa.getAssignID();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < list.size(); i++) {
                boolean exists = false;
                for (int j = 0; j < Event.eventsList.size(); j++) {
                    if (list.get(i) == Event.eventsList.get(j).getAssignID()) {
                        exists = true;
                        try {
                            Event.eventsList.get(j).setName(aa.getAssignmentTitle(list.get(i)));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            Event.eventsList.get(j).setDate(LocalDate.parse(aa.getDueDate(list.get(i))));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (!exists) {
                    String eventName;
                    try {
                        eventName = aa.getAssignmentTitle(list.get(i));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Event newEvent;
                    try {
                        newEvent = new Event(eventName, LocalDate.parse(aa.getDueDate(list.get(i))), LocalTime.parse("11:59:00"), list.get(i));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Event.eventsList.add(newEvent);
                }
            }
            return null;
        }
    }

    static class ParkingTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            parkingAccessor pa;
            try {
                pa = new parkingAccessor();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String arr[];
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