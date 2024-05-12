package com.example.appchachi.loginup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appchachi.Fire;
import com.example.appchachi.MainActivity;
import com.example.appchachi.Medic;
import com.example.appchachi.Member;
import com.example.appchachi.R;
import com.example.appchachi.Security;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout layoutEmail, layoutPassword, layoutName, layoutPhone, layoutUserID;
    private TextInputEditText etEmail, etPassword, etName, etPhone, etUserID;
    private Spinner spinnerMemberType;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutPassword = findViewById(R.id.layoutPassword);
        layoutName = findViewById(R.id.layoutName);
        layoutPhone = findViewById(R.id.layoutPhone);
        layoutUserID = findViewById(R.id.layoutUserID);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etUserID = findViewById(R.id.et_userID);
        spinnerMemberType = findViewById(R.id.spinner_memberType);
        btnSignup = findViewById(R.id.btn_signup);

        // Set up dropdown menu for member type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.member_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMemberType.setAdapter(adapter);

        // Set up click listener for signup button
        btnSignup.setOnClickListener(view -> {
            // Validate inputs and sign up user
            if (validateInputs()) {
                // Perform signup operation
                performSignup();
            }
        });
    }

    /**
     * Validates the input fields.
     *
     * @return True if inputs are valid, otherwise false.
     */
    private boolean validateInputs() {
        // Perform validation for each input field
        // Email
        if (etEmail.getText() == null || etEmail.getText().toString().trim().isEmpty()) {
            layoutEmail.setError("Email is required");
            return false;
        } else {
            layoutEmail.setError(null);
        }

        // Password
        if (etPassword.getText() == null || etPassword.getText().toString().trim().isEmpty()) {
            layoutPassword.setError("Password is required");
            return false;
        } else {
            layoutPassword.setError(null);
        }

        // Name
        if (etName.getText() == null || etName.getText().toString().trim().isEmpty()) {
            layoutName.setError("Name is required");
            return false;
        } else {
            layoutName.setError(null);
        }

        // Phone
        if (etPhone.getText() == null || etPhone.getText().toString().trim().isEmpty()) {
            layoutPhone.setError("Phone is required");
            return false;
        } else {
            layoutPhone.setError(null);
        }

        // User ID
        if (etUserID.getText() == null || etUserID.getText().toString().trim().isEmpty()) {
            layoutUserID.setError("User ID is required");
            return false;
        } else {
            layoutUserID.setError(null);
        }

        // All inputs are valid
        return true;
    }

    /**
     * Performs the signup operation.
     */
    private void performSignup() {
        // Retrieve values from input fields
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String phone = etPhone.getText() != null ? etPhone.getText().toString().trim() : "";
        String userID = etUserID.getText() != null ? etUserID.getText().toString().trim() : "";
        String memberType = spinnerMemberType.getSelectedItem().toString();
        boolean isAdmin = false; // Admin checkbox is not present in the layout

        // Create a new member account with Firebase Authentication
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign up success, save member data to Realtime Database
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (firebaseUser != null) {
                            // Create a Member object with the provided member details
                            Member member;
                            if (memberType.equals(getString(R.string.security))) {
                                member = new Security(userID, name, phone, email, true, "", isAdmin , memberType);
                            } else if (memberType.equals(getString(R.string.medic))) {
                                member = new Medic(userID, name, phone, email, true, "", isAdmin, memberType);
                            } else {
                                member = new Fire(userID, name, phone, email, true, isAdmin, memberType);
                            }

                            // Get a reference to the "members" node in the database
                            DatabaseReference membersRef = FirebaseDatabase.getInstance().getReference("members");

                            // Save the member object to the database
                            membersRef.child(firebaseUser.getUid()).setValue(member)
                                    .addOnSuccessListener(aVoid -> {
                                        // Member data saved successfully
                                        Toast.makeText(SignupActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();

                                        // Pass the member type information to MainActivity and start it
                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                        intent.putExtra("MEMBER_TYPE", memberType);
                                        startActivity(intent);

                                        // Close the SignupActivity
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Error saving member data
                                        Toast.makeText(SignupActivity.this, "Failed to save member data.", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        // If sign up fails, display a message to the user.
                        Toast.makeText(SignupActivity.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
