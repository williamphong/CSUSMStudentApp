package com.jkw.csusmstudentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.jkw.csusmstudentapp.Calendar.CalendarUtils.daysInMonthArray;
import static com.jkw.csusmstudentapp.Calendar.CalendarUtils.monthYearFromDate;

import com.jkw.csusmstudentapp.Calendar.*;
import com.jkw.csusmstudentapp.Login.Create;
import com.jkw.csusmstudentapp.dbConnector.*;


public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();



    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void weeklyAction(View view) throws SQLException {
        startActivity(new Intent(this, WeekViewActivity.class));
        new Task().execute();
    }

    public void createUser(View view) {
        startActivity(new Intent(this, Create.class));
    }

    static class Task extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            assignmentAccessor aa = null;
            try {
                aa = new assignmentAccessor();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Integer> list = null;
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
                    String eventName = null;
                    try {
                        eventName = aa.getAssignmentTitle(list.get(i));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Event newEvent = null;
                    try {
                        newEvent = new Event(eventName, LocalDate.parse(aa.getDueDate(list.get(i))), LocalTime.parse("11:59:00"), list.get(i));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Event.eventsList.add(newEvent);
                }
                else{

                }
            }
            return null;
        }
    }
}







