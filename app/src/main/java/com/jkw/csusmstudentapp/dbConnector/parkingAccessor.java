package com.jkw.csusmstudentapp.dbConnector;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class parkingAccessor {
    private static final String CONNECTION = "jdbc:mysql://10.0.2.2/";
    private final Statement stmt;
    private final String PASSWORD = "Yerinfan01:)";

    public parkingAccessor() throws SQLException {
        Properties p = new Properties();

        // retrieves password from file
        /*
        String password ="";
        try {
            File pass = new File("app/src/main/password/password.txt");
            Scanner myReader = new Scanner(pass);
            while (myReader.hasNextLine()) {
                password = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
         */

        // log into mysql db
        p.put("user", "root");
        p.put("password", PASSWORD);

        // connect to db
        Connection c = DriverManager.getConnection(CONNECTION, p);
        stmt = c.createStatement();
        System.out.println("Connected to DB");
    }


    ///////////////////////////
    // GETTERS
    ///////////////////////////

    /**
     * Returns the ID of a user specified by their email
     * This method returns the user id or a blank string if user does not exist
     *
     * @exception SQLException: throws SQL exception from parsing SQL table
     * @param assignID (String): email of user
     * @return outputTitle (string): user's ID number
     */

    /*
    public String getName() throws SQLException {
        String query = "SELECT * FROM parking_lots";
        ResultSet resultSet = statement.executeQuery(query);

        StringBuilder result = new StringBuilder();
        while(resultSet.next()){
            String lotName = resultSet.getString("lot_name");
            int availableSpots = resultSet.getInt("available_spots");

            result.append("Lot: ").append(lotName).append(", Available Spots: ").append(availableSpots).append("\n");
        }
    }

     */

    public String[] getArr() throws SQLException {
        String query = "SELECT lot_name, available_spots " +
                    "FROM SchoolAppDB.parking_lots";
        ResultSet rs = stmt.executeQuery(query);
        rs.last();
        int rowCount = rs.getRow();
        String arr[] = new String[rowCount];
        rs.first();
        for(int i = 0; i < arr.length; i++)
        {
            String lotName = rs.getString("lot_name");
            String availableSpots = rs.getString("available_spots");
            arr[i] = lotName + ": " + availableSpots;
            rs.next();
        }

        return arr;
    }
}