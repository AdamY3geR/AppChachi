package com.example.appchachi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

        // Log the start of the authentication process
        Log.d("LoginActivity", "Attempting to sign in with email: " + email);

        // Check if email and password are not empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return; // Exit the method early if either field is empty
        }

        // Perform authentication
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Authentication successful, retrieve current user ID
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                        if (currentUser != null) {
                            String currentUserId = currentUser.getUid();
                            // Proceed to retrieve member type and navigate to main activity
                            retrieveMemberType(email, currentUserId); // Pass currentUserId to retrieveMemberType
                        } else {
                            Log.e("LoginActivity", "Current user is null after successful login");
                            Toast.makeText(this, "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Authentication failed, display error message
                        Log.d("LoginActivity", "Authentication failed", task.getException());
                        Toast.makeText(this, "Authentication failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    /**
     * Retrieves the member type from the database based on the user's email.
     *
     * @param email The user's email.
     */
    private void retrieveMemberType(String email, String currentUserId) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("members");

        Query query = usersRef.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User data found, retrieve member type for the specific user
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String userId = snapshot.getKey(); // Retrieve user ID from snapshot key
                        if (userId != null && userId.equals(currentUserId)) {
                            // User found matching currentUserId, retrieve member type
                            String memberType = snapshot.child("memberType").getValue(String.class);
                            if (memberType != null) {
                                // Member type found, navigate to appropriate activity/fragment
                                navigateToMainActivity(memberType);
                                return;
                            }
                        }
                    }
                    // Member type not found for the current user
                    Toast.makeText(LoginActivity.this, "Member type not found for this user", Toast.LENGTH_SHORT).show();
                } else {
                    // No user data found for the specified email
                    Toast.makeText(LoginActivity.this, "User data doesn't exist for this email", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Log.e("FirebaseError", "Error retrieving member type: " + databaseError.getMessage());
                Toast.makeText(LoginActivity.this, "Failed to retrieve member type. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Navigates to the main activity based on the retrieved member type.
     *
     * @param memberType The user's member type.
     */
    private void navigateToMainActivity(String memberType) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("MEMBER_TYPE", memberType);
        startActivity(intent);
        finish(); // Close LoginActivity after navigating to MainActivity
    }
}