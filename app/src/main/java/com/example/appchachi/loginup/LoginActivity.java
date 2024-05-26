package com.example.appchachi.loginup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appchachi.MainActivity;
import com.example.appchachi.R;
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
    private CheckBox checkboxRememberMe;
    private SharedPreferences sharedPreferences;
    public static final String PREFS_NAME = "com.example.appchachi.loginup.PREFS";
    public static final String REMEMBER_ME_KEY = "remember_me";
    private static final String MEMBER_TYPE_KEY = "memberType";
    private static final String LAST_USER_EMAIL_KEY = "last_user_email";
    private static final String LAST_USER_ID_KEY = "last_user_id";
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        checkboxRememberMe = findViewById(R.id.checkbox_remember_me);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnSignupInstead = findViewById(R.id.btn_signup_instead);
        Button btnForgotPassword = findViewById(R.id.btn_forgot_password);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        auth = FirebaseAuth.getInstance();

        // Retrieve saved email and password if they exist
        if (sharedPreferences.getBoolean(REMEMBER_ME_KEY, false)) {
            String savedEmail = sharedPreferences.getString("email", "");
            String savedPassword = sharedPreferences.getString("password", "");
            etEmail.setText(savedEmail);
            etPassword.setText(savedPassword);
        }

        // Retrieve and set saved checkbox state
        checkboxRememberMe.setChecked(sharedPreferences.getBoolean(REMEMBER_ME_KEY, false));

        // Set up click listener for login button
        btnLogin.setOnClickListener(view -> performLogin());

        // Set up click listener for "Didn't sign up yet?" button
        btnSignupInstead.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // Set up click listener for "Forgot Password?" button
        btnForgotPassword.setOnClickListener(view -> resetPassword());
    }

    private void performLogin() {
        String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(etPassword.getText()).toString().trim();
        Log.d("LoginActivity", "Attempting to sign in with email: " + email);

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = auth.getCurrentUser();
                        if (currentUser != null) {
                            String currentUserId = currentUser.getUid();
                            retrieveMemberType(email, currentUserId);
                            sharedPreferences.edit()
                                    .putString("email", email)
                                    .putString("password", password)
                                    .putString(LAST_USER_EMAIL_KEY, email)
                                    .putString(LAST_USER_ID_KEY, currentUserId)
                                    .apply();
                            // Save checkbox state
                            sharedPreferences.edit()
                                    .putBoolean(REMEMBER_ME_KEY, checkboxRememberMe.isChecked())
                                    .apply();
                        } else {
                            Log.e("LoginActivity", "Current user is null after successful login");
                            Toast.makeText(this, "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("LoginActivity", "Authentication failed", task.getException());
                        Toast.makeText(this, "Authentication failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resetPassword() {
        String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email to reset your password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void retrieveMemberType(String email, String currentUserId) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("members");
        Query query = usersRef.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String userId = snapshot.getKey();
                        if (userId != null && userId.equals(currentUserId)) {
                            String memberType = snapshot.child("memberType").getValue(String.class);
                            if (memberType != null) {
                                if (checkboxRememberMe.isChecked()) {
                                    sharedPreferences.edit()
                                            .putBoolean(REMEMBER_ME_KEY, true)
                                            .putString(MEMBER_TYPE_KEY, memberType)
                                            .apply();
                                }
                                navigateToMainActivity(memberType);
                                return;
                            }
                        }
                    }
                    Toast.makeText(LoginActivity.this, "Member type not found for this user", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "User data doesn't exist for this email", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Error retrieving member type: " + databaseError.getMessage());
                Toast.makeText(LoginActivity.this, "Failed to retrieve member type. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToMainActivity(String memberType) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("MEMBER_TYPE", memberType);
        startActivity(intent);
        finish();
    }
}
