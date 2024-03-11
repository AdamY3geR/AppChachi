package com.example.appchachi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnSignupInstead = findViewById(R.id.btn_signup_instead);


        // Set up click listener for login button
        btnLogin.setOnClickListener(view -> {
            // Perform login
            performLogin();
        });

        // Set up click listener for "Didn't sign up yet?" button
        btnSignupInstead.setOnClickListener(view -> {
            // Navigate to the sign-up activity
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Performs the login operation.
     */
    private void performLogin() {
        // Retrieve values from input fields
        String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(etPassword.getText()).toString().trim();

        // Perform login logic here
        // For demonstration purposes, let's assume the login is successful
        // Retrieve the member type from the database or any other source
        String memberType = getMemberTypeFromDatabase(); // Replace this with your actual method to retrieve member type

        if (memberType != null) {
            // Login successful, navigate to MainActivity and pass the member type
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("MEMBER_TYPE", memberType);
            startActivity(intent);
            finish(); // Close LoginActivity
        } else {
            // Handle the case where member type is not available
            Toast.makeText(this, "Failed to retrieve member type", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to retrieve the member type from the database.
     * Replace this with your actual implementation.
     */
    private String getMemberTypeFromDatabase() {
        // This is a placeholder method, replace it with your actual logic
        // For demonstration purposes, return a hardcoded member type
        return "Security"; // Replace with actual member type retrieved from the database
    }

}
