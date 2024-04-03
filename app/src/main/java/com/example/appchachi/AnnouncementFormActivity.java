package com.example.appchachi;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for creating and sending announcements.
 */
public class AnnouncementFormActivity extends AppCompatActivity {
    private EditText announcementEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_form);

        announcementEditText = findViewById(R.id.et_message);
        Button btnSend = findViewById(R.id.btn_send);

        // Dropdown menu for selecting recipients
        String[] recipientsArray = {"Security", "Medic", "Fire", "All"};
        MaterialAutoCompleteTextView dropdownRecipients = findViewById(R.id.dd_recipients);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dpd_item_announcement, recipientsArray);
        dropdownRecipients.setAdapter(adapter);

        btnSend.setOnClickListener(v -> {
            String announcementMessage = announcementEditText.getText().toString().trim();
            String selectedRecipient = dropdownRecipients.getText().toString().trim(); // Rename to selectedRecipient

            if (!announcementMessage.isEmpty()) {
                List<String> recipients = new ArrayList<>(); // Define the recipients list here
                recipients.add(selectedRecipient); // Add selected recipient to the list

                // Push new announcement with recipients to Firebase Realtime Database
                DatabaseReference announcementsRef = FirebaseDatabase.getInstance().getReference("announcements");
                String key = announcementsRef.push().getKey(); // Generate unique key for the announcement
                assert key != null;
                Announcement newAnnouncement = new Announcement("SenderName", announcementMessage, recipients);
                announcementsRef.child(key).setValue(newAnnouncement)
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