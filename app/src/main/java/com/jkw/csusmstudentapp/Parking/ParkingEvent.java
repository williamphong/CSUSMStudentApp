package com.jkw.csusmstudentapp.Parking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ParkingEvent
{
    public static ArrayList<ParkingEvent> parkingEventsList = new ArrayList<>();


    private String name;

    private int spots;

    public ParkingEvent(String name, int spots)
    {
        this.name = name;
        this.spots = spots;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public int getSpots()
    {
        return spots;
    }
}