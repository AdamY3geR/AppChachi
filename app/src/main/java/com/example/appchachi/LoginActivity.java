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
        // For demonstration purposes, display a toast message
        String message = "Login successful!\n" +
                "Email: " + email + "\n" +
                "Password: " + password;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
