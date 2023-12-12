package com.example.andy_try;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements RegisterAsyncTask.RegisterCallback {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextGender;
    private EditText editTextMobile;
    private EditText editTextPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Update this line

        // Initialize UI elements
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextGender = findViewById(R.id.editTextGender);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Set click listener for the register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String username = editTextUsername.getText().toString();
                String email = editTextEmail.getText().toString();
                String gender = editTextGender.getText().toString();
                String mobile = editTextMobile.getText().toString();
                String password = editTextPassword.getText().toString();

                // Call the method to register the user
                registerUser(username, email, gender, mobile, password);
            }
        });
    }

    private void registerUser(String username, String email, String gender, String mobile, String password) {
        RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask(this);
        registerAsyncTask.execute(username, email, gender, mobile, password);
    }

    @Override
    public void onSuccess() {
        // User registered successfully, handle success
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String errorMessage) {
        // Handle registration error
        Toast.makeText(this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
    }
}
