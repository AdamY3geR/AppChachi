package com.example.appchachi;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnouncementFormActivity extends AppCompatActivity {

    private EditText announcementEditText;
    private MaterialAutoCompleteTextView dropdownRecipients;
    private DatabaseReference membersRef;
    private DatabaseReference memberTokensRef;
    private final String[] validRecipients = {"Security", "Medic", "Fire", "All"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_form);

        NotificationUtils.createNotificationChannel(this);

        announcementEditText = findViewById(R.id.et_message);
        Button btnSend = findViewById(R.id.btn_send);

        membersRef = FirebaseDatabase.getInstance().getReference("members");
        memberTokensRef = FirebaseDatabase.getInstance().getReference("memberTokens");

        dropdownRecipients = findViewById(R.id.dd_recipients);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, validRecipients);
        dropdownRecipients.setAdapter(adapter);

        btnSend.setOnClickListener(v -> {
            String announcementMessage = announcementEditText.getText().toString().trim();
            String selectedRecipient = dropdownRecipients.getText().toString().trim();

            if (!announcementMessage.isEmpty() && isValidRecipient(selectedRecipient)) {
                getRecipientsAndSendAnnouncement(selectedRecipient, announcementMessage);
            } else {
                Toast.makeText(AnnouncementFormActivity.this, "Please enter a valid announcement and select a recipient", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidRecipient(String recipient) {
        for (String validRecipient : validRecipients) {
            if (validRecipient.equalsIgnoreCase(recipient)) {
                return true;
            }
        }
        return false;
    }

    private void getRecipientsAndSendAnnouncement(String selectedRecipient, String announcementMessage) {
        List<String> recipients = new ArrayList<>();

        if (selectedRecipient.equalsIgnoreCase("All")) {
            membersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String memberType = snapshot.child("memberType").getValue(String.class);
                        if (memberType != null && isValidRecipient(memberType)) {
                            recipients.add(memberType);
                        }
                    }
                    sendAnnouncement(announcementMessage, recipients);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled
                    Toast.makeText(AnnouncementFormActivity.this, "Failed to fetch member types", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            recipients.add(selectedRecipient);
            sendAnnouncement(announcementMessage, recipients);
        }
    }

    private void sendAnnouncement(String announcementMessage, List<String> recipients) {
        DatabaseReference announcementsRef = FirebaseDatabase.getInstance().getReference("announcements");
        String key = announcementsRef.push().getKey();

        if (key != null) {
            Announcement newAnnouncement = new Announcement("SenderName", announcementMessage, recipients);
            announcementsRef.child(key).setValue(newAnnouncement)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(AnnouncementFormActivity.this, "Announcement sent", Toast.LENGTH_SHORT).show();
                            announcementEditText.setText(""); // Clear input field
                            sendNotifications(recipients);
                        } else {
                            Toast.makeText(AnnouncementFormActivity.this, "Failed to send announcement", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void sendNotifications(List<String> recipients) {
        for (String recipient : recipients) {
            memberTokensRef.child(recipient).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String fcmToken = dataSnapshot.getValue(String.class);
                        if (fcmToken != null) {
                            sendNotificationToToken(fcmToken);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled
                }
            });
        }
    }

    private void sendNotificationToToken(String fcmToken) {
        Map<String, String> notificationData = new HashMap<>();
        notificationData.put("title", "New Announcement");
        notificationData.put("message", announcementEditText.getText().toString().trim());

        RemoteMessage message = new RemoteMessage.Builder(fcmToken)
                .setData(notificationData)
                .build();

        FirebaseMessaging.getInstance().send(message);
    }
}
