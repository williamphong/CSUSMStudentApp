package com.jkw.csusmstudentapp.Login;

import com.jkw.csusmstudentapp.dbConnector.userAccessor;

import java.sql.SQLException;

public class LoginManager {

    private final userAccessor dbc;

    public LoginManager() throws SQLException {
        dbc = new userAccessor();
    }

    public boolean validateUser(String email, String password) throws SQLException {
        String userID = dbc.getUserID(email);
        if (!userID.isEmpty()) {
            String storedPassword = dbc.getPassword(userID);
            return password.equals(storedPassword);
        }
        return false;
    }

    public String getFullName(String email) throws SQLException {
        String userID = dbc.getUserID(email);
        String firstName = dbc.getFirstName(userID);
        String lastName = dbc.getLastName(userID);
        return firstName + " " + lastName;
    }

    public String getUserType(String email) throws SQLException {
        String userID = dbc.getUserID(email);
        return dbc.getUserType(userID);
    }

    public static void main(String[] args) {
        try {
            LoginManager loginManager = new LoginManager();
            String email = "test@csusm.edu";
            String password = "password2";

            if (loginManager.validateUser(email, password)) {
                String userType = loginManager.getUserType(email);
                System.out.println("Login successful. User type: " + userType);
            } else {
                System.out.println("Login failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
