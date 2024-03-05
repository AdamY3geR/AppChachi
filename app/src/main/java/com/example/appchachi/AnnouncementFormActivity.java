package com.example.appchachi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnnouncementFormActivity extends AppCompatActivity {
    private EditText announcementEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_form);

        announcementEditText = findViewById(R.id.et_message);
        Button btnSend = findViewById(R.id.btn_send);


        btnSend.setOnClickListener(v -> {
            String announcementMessage = announcementEditText.getText().toString().trim();
            if (!announcementMessage.isEmpty()) {
                // Push new announcement to Firebase Realtime Database
                DatabaseReference announcementsRef = FirebaseDatabase.getInstance().getReference("announcements");
                String key = announcementsRef.push().getKey(); // Generate unique key for the announcement
                assert key != null;
                announcementsRef.child(key).setValue(announcementMessage)
                        .addOnSuccessListener(aVoid -> {
                            // Announcement successfully added
                            Toast.makeText(AnnouncementFormActivity.this, "Announcement sent", Toast.LENGTH_SHORT).show();
                            announcementEditText.setText(""); // Clear input field
                        })
                        .addOnFailureListener(e -> {
                            // Failed to add announcement
                            Toast.makeText(AnnouncementFormActivity.this, "Failed to send announcement", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(AnnouncementFormActivity.this, "Please enter an announcement", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
