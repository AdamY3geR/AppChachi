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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activity for creating and sending announcements.
 */
public class AnnouncementFormActivity extends AppCompatActivity {

    private EditText announcementEditText;
    private MaterialAutoCompleteTextView dropdownRecipients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_form);

        announcementEditText = findViewById(R.id.et_message);
        Button btnSend = findViewById(R.id.btn_send);

        // Dropdown menu for selecting recipients
        String[] recipientsArray = {"Security", "Medic", "Fire", "All"};
        dropdownRecipients = findViewById(R.id.dd_recipients);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, recipientsArray);
        dropdownRecipients.setAdapter(adapter);

        // Show dropdown list when clicking on the recipient field
        dropdownRecipients.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dropdownRecipients.showDropDown();
            }
        });

        dropdownRecipients.setOnClickListener(v -> dropdownRecipients.showDropDown());

        btnSend.setOnClickListener(v -> {
            String announcementMessage = announcementEditText.getText().toString().trim();
            String selectedRecipient = dropdownRecipients.getText().toString().trim(); // Get selected recipient

            if (!announcementMessage.isEmpty() && isValidRecipient(selectedRecipient, recipientsArray)) {
                List<String> recipients = new ArrayList<>();
                recipients.add(selectedRecipient);

                // Push new announcement with recipients to Firebase Realtime Database
                DatabaseReference announcementsRef = FirebaseDatabase.getInstance().getReference("announcements");
                String key = announcementsRef.push().getKey();

                if (key != null) {
                    Announcement newAnnouncement = new Announcement("SenderName", announcementMessage, recipients);
                    announcementsRef.child(key).setValue(newAnnouncement)
                            .addOnSuccessListener(aVoid -> {
                                // Announcement successfully added
                                sendNotifications(recipients, announcementMessage); // Send notifications
                                Toast.makeText(AnnouncementFormActivity.this, "Announcement sent", Toast.LENGTH_SHORT).show();
                                announcementEditText.setText(""); // Clear input field
                            })
                            .addOnFailureListener(e -> {
                                // Failed to add announcement
                                Toast.makeText(AnnouncementFormActivity.this, "Failed to send announcement", Toast.LENGTH_SHORT).show();
                            });
                }
            } else {
                Toast.makeText(AnnouncementFormActivity.this, "Please enter a valid announcement and select a recipient", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to validate selected recipient
    private boolean isValidRecipient(String recipient, String[] validRecipients) {
        for (String validRecipient : validRecipients) {
            if (validRecipient.equalsIgnoreCase(recipient)) {
                return true;
            }
        }
        return false;
    }

    // Method to send notifications to specific member types
    private void sendNotifications(List<String> recipients, String announcementMessage) {
        // Loop through the recipients and send notifications
        for (String recipient : recipients) {
            // Construct the notification payload (you can customize this based on your needs)
            Map<String, String> notificationData = new HashMap<>();
            notificationData.put("title", "New Announcement");
            notificationData.put("message", announcementMessage);

            // Assuming you have a topic or token for each recipient (e.g., based on member type)
            String recipientTopic = getRecipientTopic(recipient); // Implement this method to get topic based on recipient

            if (recipientTopic != null) {
                // Build the RemoteMessage
                RemoteMessage message = new RemoteMessage.Builder(recipientTopic)
                        .setData(notificationData) // Set data payload
                        .build();

                // Send the message using FirebaseMessaging
                FirebaseMessaging.getInstance().send(message)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Notification sent successfully
                                Toast.makeText(AnnouncementFormActivity.this, "Notification sent", Toast.LENGTH_SHORT).show();
                            } else {
                                // Failed to send notification
                                Toast.makeText(AnnouncementFormActivity.this, "Failed to send notification", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }





    // Method to get the FCM topic for a given recipient (e.g., based on member type)
    private String getRecipientTopic(String recipient) {
        switch (recipient.toLowerCase()) {
            case "security":
                return "Security";
            case "medic":
                return "Medic";
            case "fire":
                return "Fire";
            case "all":
                return "All";
            default:
                return null;
        }
    }

}
