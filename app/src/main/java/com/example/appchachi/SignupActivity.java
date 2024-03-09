package com.example.appchachi;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout layoutEmail, layoutPassword, layoutName, layoutPhone, layoutUserID, layoutMemberType;
    private TextInputEditText etEmail, etPassword, etName, etPhone, etUserID;
    private MaterialAutoCompleteTextView ddMemberType;
    private CheckBox cbAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutPassword = findViewById(R.id.layoutPassword);
        layoutName = findViewById(R.id.layoutName);
        layoutPhone = findViewById(R.id.layoutPhone);
        layoutUserID = findViewById(R.id.layoutUserID); // Updated reference
        layoutMemberType = findViewById(R.id.layoutMemberType);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etUserID = findViewById(R.id.et_userID); // Updated reference
        ddMemberType = findViewById(R.id.dd_memberType);
        cbAdmin = findViewById(R.id.cb_admin);
        Button btnSignup = findViewById(R.id.btn_signup);

        // Set up dropdown menu for member type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.member_types_array, android.R.layout.simple_dropdown_item_1line);
        ddMemberType.setAdapter(adapter);

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
        if (etEmail.getText() == null || etEmail.getText().toString().trim().isEmpty()) {
            layoutEmail.setError("Email is required");
            return false;
        } else {
            layoutEmail.setError(null);
        }

        if (etPassword.getText() == null || etPassword.getText().toString().trim().isEmpty()) {
            layoutPassword.setError("Password is required");
            return false;
        } else {
            layoutPassword.setError(null);
        }

        if (etName.getText() == null || etName.getText().toString().trim().isEmpty()) {
            layoutName.setError("Name is required");
            return false;
        } else {
            layoutName.setError(null);
        }

        if (etPhone.getText() == null || etPhone.getText().toString().trim().isEmpty()) {
            layoutPhone.setError("Phone is required");
            return false;
        } else {
            layoutPhone.setError(null);
        }

        if (etUserID.getText() == null || etUserID.getText().toString().trim().isEmpty()) {
            layoutUserID.setError("User ID is required");
            return false;
        } else {
            layoutUserID.setError(null);
        }

        if (ddMemberType.getText() == null || ddMemberType.getText().toString().trim().isEmpty()) {
            layoutMemberType.setError("Member type is required");
            return false;
        } else {
            layoutMemberType.setError(null);
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
        String memberType = ddMemberType.getText() != null ? ddMemberType.getText().toString().trim() : "";
        boolean isAdmin = cbAdmin.isChecked();

        // Create a new member account with Firebase Authentication
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign up success, save member data to Firestore
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (firebaseUser != null) {
                            // Create a Member object with the provided member details
                            Member member;
                            if (memberType.equals(getString(R.string.security))) {
                                member = new Security(userID, name, phone, email, true, "", isAdmin);
                            } else if (memberType.equals(getString(R.string.medic))) {
                                member = new Medic(userID, name, phone, email, true, "", isAdmin);
                            } else {
                                member = new Fire(userID, name, phone, email, true, isAdmin);
                            }

                            // Save the member object to Firestore
                            FirebaseFirestore.getInstance().collection("members")
                                    .document(firebaseUser.getUid())
                                    .set(member)
                                    .addOnSuccessListener(aVoid -> {
                                        // Member data saved successfully
                                        Toast.makeText(SignupActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                                        // Proceed to the next step in your app
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
