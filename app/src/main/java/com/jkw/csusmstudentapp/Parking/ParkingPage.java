package com.jkw.csusmstudentapp.Parking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jkw.csusmstudentapp.R;

import com.jkw.csusmstudentapp.dbConnector.parkingAccessor;
import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ParkingPage extends AppCompatActivity {
    private static final String url = "jdbc:mysql://10.0.2.2/";
    private static final String username = "root";
    private static final String password = "Yerinfan01:)";

    public static String[] parkArr;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking);

        //new DatabaseTask().execute();

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.parkingspots, R.id.itemTextView, parkArr);

        ListView listView = (ListView) findViewById(R.id.ParkingList);
        listView.setAdapter(adapter);

    }


}