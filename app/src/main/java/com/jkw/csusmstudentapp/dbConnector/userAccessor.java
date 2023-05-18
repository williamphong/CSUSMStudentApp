package com.jkw.csusmstudentapp.dbConnector;

import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Properties;
import java.sql.*;
import java.io.File;
import java.util.Scanner;

public class userAccessor {
    private static final String CONNECTION = "jdbc:mysql://10.0.2.2/";
    private final Connection c;
    private final Statement stmt;


    ///////////////////////////
    // CONSTRUCTOR
    ///////////////////////////
    public userAccessor() throws SQLException {
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
        c = DriverManager.getConnection(CONNECTION,p);
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
     * @param email (String): email of user
     * @return outputID (string): user's ID number
     */
    public String getUserID(String email) throws SQLException {
        String outputID = "";
        String sql = "SELECT id, email " +
                "FROM SchoolAppDB.User";

        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            //System.out.println(rs.getString("id"));
            if(Objects.equals(rs.getString("email"), email)){
                outputID = rs.getString("id");
                return outputID;
            }
        }
        return outputID;
    }


    // TEMP
    // NOT RLY A USE FOR THIS
    public void getUsers() throws SQLException {
        String sql = "SELECT id, first_name, last_name " +
                "FROM SchoolAppDB.User";

        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            System.out.println(rs.getString("id") + "\t" +
                    rs.getString("first_name")  + "\t" +
                    rs.getString("last_name"));
        }
    }


    /**
     * Returns the first name of a user specified by their id
     * This method returns the user's first name or a blank string if user does not exist
     *
     * @exception SQLException: throws SQL exception from parsing SQL table
     * @param id (String): id of user
     * @return outputFirst (string): user's first name
     */
    public String getFirstName(String id) throws SQLException {
        String outputFirst = "";
        String sql = "SELECT id, first_name " +
                "FROM SchoolAppDB.User";

        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            //System.out.println(rs.getString("id"));
            if(Objects.equals(rs.getString("id"), id)){
                outputFirst = rs.getString("first_name");
                return outputFirst;
            }
        }

        return outputFirst;
    }

    /**
     * Returns the last name of a user specified by their id
     * This method returns the user's last name or a blank string if user does not exist
     *
     * @exception SQLException: throws SQL exception from parsing SQL table
     * @param id (String): id of user
     * @return outputFirst (string): user's last name
     */
    public String getLastName(String id) throws SQLException {
        String outputLast = "";
        String sql = "SELECT id, last_name " +
                "FROM SchoolAppDB.User";

        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            //System.out.println(rs.getString("id"));
            if(Objects.equals(rs.getString("id"), id)){
                outputLast = rs.getString("last_name");
                return outputLast;
            }
        }

        return outputLast;
    }

    /**
     * Returns the email of the user specified by their id
     * This method returns the user's email or a blank string if user does not exist
     *
     * @exception SQLException: throws SQL exception from parsing SQL table
     * @param id (String): id of user
     * @return outputEmail (string): user's email
     */
    public String getEmail(String id) throws SQLException {
        String outputEmail = "";
        String sql = "SELECT id, email " +
                "FROM SchoolAppDB.User";

        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            //System.out.println(rs.getString("id"));
            if(Objects.equals(rs.getString("id"), id)){
                outputEmail = rs.getString("email");
                return outputEmail;
            }
        }

        return outputEmail;
    }

    /**
     * Returns the email of the user specified by their id
     * This method returns the user's email or a blank string if user does not exist
     *
     * @exception SQLException: throws SQL exception from parsing SQL table
     * @param id (String): id of user
     * @return outputEmail (string): user's email
     */
    public String getUserType(String id) throws SQLException {
        String outputType = "";
        String sql = "SELECT id, user_type " +
                "FROM SchoolAppDB.User";

        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            //System.out.println(rs.getString("id"));
            if(Objects.equals(rs.getString("id"), id)){
                outputType = rs.getString("user_type");
                return outputType;
            }
        }

        return outputType;
    }


    /**
     * Returns the password from the userID/email
     * @param userID (String): id of user
     * @return passwword (string): user's password
     * @throws SQLException throws sql
     */
    public String getPassword(String userID) throws SQLException {
        String getPasswordQuery = "SELECT password FROM users WHERE userID= ?";
        try (PreparedStatement preparedStatement = c.prepareStatement(getPasswordQuery)) {
            preparedStatement.setString(1, userID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("password");
                }
            }
        }
        return "";
    }


    ///////////////////////////
    // SETTERS
    ///////////////////////////

    /**
     * Adds a new user to the database
     * This method requires all the information before a user can be added
     *
     * @exception SQLException: throws SQL exception from parsing SQL table
     * @param id (String): id of user
     * @param first (String): user first name
     * @param last (String): user last name
     * @param email (String): user email
     * @param password (String): password
     * @param userType (String): user type
     */
    public void addUser(String id, String first, String last, String email, String password, String userType) throws SQLException {
        if(Objects.equals(getEmail(id), "")){
            String sql = "INSERT INTO SchoolAppDB.User " +
                    "(id, first_name, last_name, email, password, user_type) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, first);
            pstmt.setString(3, last);
            pstmt.setString(4, email);
            pstmt.setString(5, password);
            pstmt.setString(6, userType);
            pstmt.executeUpdate();

        }
    }




}
