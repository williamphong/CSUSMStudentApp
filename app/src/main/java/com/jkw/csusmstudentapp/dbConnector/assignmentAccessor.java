package com.jkw.csusmstudentapp.dbConnector;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class assignmentAccessor {
    private static final String CONNECTION = "jdbc:mysql://10.0.2.2/";
    private final Statement stmt;

    public assignmentAccessor() throws SQLException {
        Properties p = new Properties();

        // retrieves password from file
        String password ="";
        try {
            File pass = new File("src/main/resources/password.txt");
            Scanner myReader = new Scanner(pass);
            while (myReader.hasNextLine()) {
                password = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // log into mysql db
        p.put("user", "root");
        p.put("password", "");

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
    public String getAssignmentTitle(int assignID) throws SQLException {
        String outputTitle = "";
        String sql = "SELECT title, assignID " +
                "FROM SchoolAppDB.Assignments";

        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            //System.out.println(rs.getString("id"));
            if(Objects.equals(rs.getInt("assignID"), assignID)){
                outputTitle = rs.getString("title");
                return outputTitle;
            }
        }
        return outputTitle;
    }

    /**
     * Returns the ID of a user specified by their email
     * This method returns the user id or a blank string if user does not exist
     *
     * @exception SQLException: throws SQL exception from parsing SQL table
     * @param assignID (String): email of user
     * @return outputTitle (string): user's ID number
     */
    public String getDueDate(int assignID) throws SQLException {
        String outputTitle = "";
        String sql = "SELECT due_date, assignID " +
                "FROM SchoolAppDB.Assignments";

        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            //System.out.println(rs.getString("id"));
            if(Objects.equals(rs.getInt("assignID"), assignID)){
                outputTitle = rs.getString("due_date");
                return outputTitle;
            }
        }
        return outputTitle;
    }

    /**s
     * Returns the ID of a user specified by their email
     * This method returns the user id or a blank string if user does not exist
     *
     * @exception SQLException: throws SQL exception from parsing SQL table
     * @param assignID (String): email of user
     * @return outputTitle (string): user's ID number
     */
    public String getDescription(int assignID) throws SQLException {
        String outputDesc = "";
        String sql = "SELECT description, assignID " +
                "FROM SchoolAppDB.Assignments";

        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            //System.out.println(rs.getString("id"));
            if(Objects.equals(rs.getInt("assignID"), assignID)){
                outputDesc = rs.getString("description");
                return outputDesc;
            }
        }
        return outputDesc;
    }


    public ArrayList<Integer> getAssignID() throws SQLException {
        ArrayList<Integer> id = new ArrayList<>();
        String sql = "SELECT description, assignID " +
                "FROM SchoolAppDB.Assignments";

        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            //System.out.println(rs.getString("id"));
            id.add(rs.getInt(2));
        }
        return id;
    }

}
