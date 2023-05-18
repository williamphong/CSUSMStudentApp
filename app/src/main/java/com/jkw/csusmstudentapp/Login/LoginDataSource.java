package com.jkw.csusmstudentapp.Login;
import android.os.AsyncTask;
import android.util.Log;

import com.jkw.csusmstudentapp.dbConnector.userAccessor;
import com.jkw.csusmstudentapp.Login.model.LoggedInUser;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private userAccessor dbc ;
    static String email;
    static String password;
    static boolean verification = false;

    public LoginDataSource() {
        try {
            dbc = new userAccessor();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validateUser(String email, String password) throws SQLException {
        this.email = email;
        this.password = password;
        Log.d("MY MESSAGE!!", "USER" +email + " pass"+password);


        new Task().execute();

        return verification;
    }

    static class Task extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            userAccessor ua;
            try {
                ua = new userAccessor();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String userID;
            try {
                userID = ua.getUserID(email);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if(!userID.isEmpty()) {
                String storedPassword;
                try {
                    storedPassword = ua.getPassword(userID);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                String hashedPassword = PasswordUtil.hashPassword(password);
                //Log.d("MY MESSAGE!!", hashedPassword);
                assert hashedPassword != null;
                verification = hashedPassword.equals(storedPassword);
            }
            return null;
        }
    }

    public String getFullName(String email) throws SQLException {
        String userID = dbc.getUserID(email);
        String firstName = dbc.getFirstName(userID);
        String lastName = dbc.getLastName(userID);
        return firstName + " " + lastName;
    }

    public Result<LoggedInUser> login(String username, String password) {

        try {
            //Log.d("MY MESSAGE!!", "LOGINDS" + password);
            if (validateUser(username, password)) {
                Log.d("MY MESSAGE!!", "SUCCESS");
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