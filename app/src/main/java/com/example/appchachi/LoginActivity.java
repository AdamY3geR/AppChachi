package com.example.appchachi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;

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

        // Check if email and password are not empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return; // Exit the method early if either field is empty
        }

        // Perform authentication
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Authentication successful, retrieve member type and navigate to MainActivity
                        retrieveMemberType(email);
                    } else {
                        // Authentication failed, display an error message
                        Toast.makeText(this, "Authentication failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Retrieves the member type from the database.
     *
     * @param email The user's email.
     */
    private void retrieveMemberType(String email) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String memberType = snapshot.child("memberType").getValue(String.class);
                        if (memberType != null) {
                            // Member type found, navigate to MainActivity and pass the member type
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("MEMBER_TYPE", memberType);
                            startActivity(intent);
                            finish(); // Close LoginActivity
                            return; // Exit the method
                        }
                    }
                }
                // If member type is not found or user doesn't exist
                Toast.makeText(LoginActivity.this, "Failed to retrieve member type", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Failed to retrieve member type", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
