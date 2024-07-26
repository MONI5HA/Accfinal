package com.example.androidapp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {


    private EditText editTextUsername, editTextPassword ,editTextemail , editTextphone;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextemail = findViewById(R.id.editTextUseremail);
        editTextphone = findViewById(R.id.editTextUserPhone);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Set a click listener for the login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve entered username and password
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

              // Implement authentication logic here
                if(validateInputs()){
                    if (username.equals("Admin") && password.equals("Testing01$")) {
                        // Successful login
                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainPage.class);
                        startActivity(intent);
                    } else {
                        // Failed login
                        Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }


               Intent intent = new Intent(getApplicationContext(), MainPage.class);
               //startActivity(intent);
            }
        });
    }
    private boolean validateInputs() {
        String email = editTextemail.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phone = editTextphone.getText().toString().trim();

        if (!isValidEmail(email)) {
            editTextemail.setError("Invalid email address");
            editTextemail.requestFocus();
            return false;
        }

        if (!isValidUsername(username)) {
            editTextUsername.setError("Invalid username");
            editTextUsername.requestFocus();
            return false;
        }

        if (!isValidPassword(password)) {
            editTextPassword.setError("Invalid password -At least 8 characters long.\n" +
                    "Contains at least one uppercase letter.\n" +
                    "Contains at least one lowercase letter.\n" +
                    "Contains at least one special character (e.g., !@#$%^&*()_+-=[]{}|;':\",./<>?)");
            editTextPassword.requestFocus();
            return false;
        }

        if (!isValidPhone(phone)) {
            editTextphone.setError("Invalid phone number");
            editTextphone.requestFocus();
            return false;
        }

        Toast.makeText(this, "All inputs are valid!", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return !TextUtils.isEmpty(email) && Pattern.matches(emailRegex,email);
    }

    private boolean isValidUsername(String username) {
        String usernamePattern = "^[a-zA-Z0-9._-]{3,}$";
        return !TextUtils.isEmpty(username) && Pattern.matches(usernamePattern, username);
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#^$*+=!])(?=\\S+$).{8,}$";
        return !TextUtils.isEmpty(password) && Pattern.matches(passwordPattern, password);
    }

    private boolean isValidPhone(String phone) {
        String phonePattern = "^(\\+)?[0-9]{10,13}$";
        return !TextUtils.isEmpty(phone) && Pattern.matches(phonePattern, phone);
    }
}