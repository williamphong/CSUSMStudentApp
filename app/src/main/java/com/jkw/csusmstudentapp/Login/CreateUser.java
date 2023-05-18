package com.jkw.csusmstudentapp.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jkw.csusmstudentapp.MainActivity;
import com.jkw.csusmstudentapp.R;
import com.jkw.csusmstudentapp.dbConnector.userAccessor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Random;

public class CreateUser extends AppCompatActivity {
    static String username;
    static String hashedPassword;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
        Button button = findViewById(R.id.login);

        EditText editTextUsername = findViewById(R.id.username);
        EditText editTextPassword = findViewById(R.id.password);

        button.setOnClickListener(v -> {
            username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            boolean emailCheck = false, passCheck = false;
            if(username.contains("@csusm.edu") && !username.isEmpty()){
                emailCheck = true;
            }
            if(password.length()>7){
                passCheck = true;
            }


            if(!emailCheck || !passCheck) {
                Toast.makeText(CreateUser.this,"Please enter a valid username or password", Toast.LENGTH_SHORT).show();
            }
            else{
                hashedPassword = hashPassword(password);
                new Task().execute();

                Toast.makeText(CreateUser.this,"Account successfully created", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreateUser.this, MainActivity.class));
                finish();
            }

        });


    }

    static class Task extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            userAccessor ua = null;
            try {
                ua = new userAccessor();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                Random r = new Random();
                int low = 1;
                int high = 1000;
                int result = r.nextInt(high-low) + low;

                ua.addUser(Integer.toString(result), "first", "last",
                        username, hashedPassword, "student");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }
    private String hashPassword(String password) {
        try {
            // Create message digest object for SHA-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Generate hash from password bytes
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert hash bytes to hexadecimal representation
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
