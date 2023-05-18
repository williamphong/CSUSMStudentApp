package com.jkw.csusmstudentapp.data;
import com.jkw.csusmstudentapp.dbConnector.userAccessor;
import com.jkw.csusmstudentapp.data.model.LoggedInUser;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private userAccessor dbc;

    public LoginDataSource() {
        try {
            dbc = new userAccessor();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public Result<LoggedInUser> login(String username, String password) {
        try {
            if (validateUser(username, password)) {
                String displayName = getFullName(username);
                LoggedInUser user = new LoggedInUser(java.util.UUID.randomUUID().toString(), displayName);
                return new Result.Success<>(user);
            } else {
                return new Result.Error(new IOException("Invalid credentials"));
            }
        } catch (SQLException e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }



    public void logout() {
        // TODO: revoke authentication
    }
}