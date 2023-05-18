package com.jkw.csusmstudentapp.Login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jkw.csusmstudentapp.R;
import com.jkw.csusmstudentapp.dbConnector.userAccessor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Random;

public class Create extends AppCompatActivity {
    static String username;
    static String hashedPassword;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
        Button button = findViewById(R.id.login);

        EditText editTextUsername = findViewById(R.id.username);
        EditText editTextPassword = findViewById(R.id.password);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();


                if(username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Create.this,"Please enter user and password", Toast.LENGTH_SHORT).show();
                }

                hashedPassword = hashPassword(password);


                new Task().execute();

                Toast.makeText(Create.this,"User successfully made", Toast.LENGTH_SHORT).show();
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

                ua.addUser(Integer.toString(result), "test", "test",
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
