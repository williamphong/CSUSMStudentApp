package com.jkw.csusmstudentapp.Parking;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.jkw.csusmstudentapp.Calendar.CalendarUtils;
import com.jkw.csusmstudentapp.Calendar.Event;
import com.jkw.csusmstudentapp.R;


public class ParkingAdapter extends ArrayAdapter<ParkingEvent>
{

    public ParkingAdapter(@NonNull Context context, List<ParkingEvent> events) throws SQLException {
        super(context, 0, events);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        ParkingEvent event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        String eventTitle = event.getName() +" "+ event.getSpots();
        eventCellTV.setText(eventTitle);
        return convertView;
    }
}