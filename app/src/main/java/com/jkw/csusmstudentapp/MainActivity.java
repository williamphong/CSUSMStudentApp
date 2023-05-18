package com.jkw.csusmstudentapp;

import static com.jkw.csusmstudentapp.Calendar.CalendarUtils.daysInMonthArray;
import static com.jkw.csusmstudentapp.Calendar.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jkw.csusmstudentapp.Calendar.CalendarAdapter;
import com.jkw.csusmstudentapp.Calendar.CalendarUtils;
import com.jkw.csusmstudentapp.Calendar.Event;
import com.jkw.csusmstudentapp.Calendar.WeekViewActivity;
import com.jkw.csusmstudentapp.Parking.ParkingPage;
import com.jkw.csusmstudentapp.dbConnector.assignmentAccessor;
import com.jkw.csusmstudentapp.dbConnector.parkingAccessor;
import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view){
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view){
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date){
        if(date != null){
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeekViewActivity.class));
        new Task().execute();
        new DatabaseTask().execute();
    }
    public void parkingAction(View view) {
        startActivity(new Intent(this, ParkingPage.class));
        new DatabaseTask().execute();
    }


    static class Task extends AsyncTask<Void, Void, Void>{
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
            for(int i = 0; i < list.size(); i++){
                boolean exists = false;
                for(int j = 0; j < Event.eventsList.size(); j++){
                    if(list.get(i) == Event.eventsList.get(j).getAssignID()){
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
                if(!exists){
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
/*
    static class ParkingTask extends AsyncTask<Void, Void, String> {

        @Override
        protected Void doInBackground(Void... voids) {
            parkingAccessor pa;
            try {
                pa = new parkingAccessor();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            Event newEvent;
            try {

                newEvent = new Event(pa.getName(), LocalDate.parse(aa.getDueDate(list.get(i))), LocalTime.parse("11:59:00"), list.get(i));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Event.eventsList.add(newEvent);

            return null;
        }

    }

 */
static class DatabaseTask extends AsyncTask<Void, Void, Void>{

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







