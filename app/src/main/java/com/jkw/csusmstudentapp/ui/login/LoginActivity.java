package com.jkw.csusmstudentapp.ui.login;


import static com.jkw.csusmstudentapp.Login.PasswordUtil.hashPassword;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jkw.csusmstudentapp.Login.CreateUser;
import com.jkw.csusmstudentapp.MainActivity;
import com.jkw.csusmstudentapp.databinding.ActivityLoginBinding;
import com.jkw.csusmstudentapp.dbConnector.userAccessor;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    static String username;
    String password;
    static String hashedPassword;
    boolean loggedIn=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                boolean emailCheck = false, passCheck = false;
                if(username.contains("@csusm.edu") && !username.isEmpty()){
                    emailCheck = true;
                }
                if(password.length()>7){
                    passCheck = true;
                }

                if(!emailCheck || !passCheck) {
                    Toast.makeText(LoginActivity.this,"Please enter a valid username or password", Toast.LENGTH_SHORT).show();
                }
                else{
                    hashedPassword = hashPassword(password);

                    new Task().execute();

                    if(loggedIn){
                        Toast.makeText(LoginActivity.this,"Successfully logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    class Task extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            userAccessor uaa;
            try {
                uaa = new userAccessor();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                String userID = uaa.getUserID(username);
                String pass = uaa.getPassword(userID);
                if(pass.equals(hashedPassword)){
                    loggedIn=true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    public void createAction(View view){
        startActivity(new Intent(this, CreateUser.class));
    }
}