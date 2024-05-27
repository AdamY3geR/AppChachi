package com.example.appchachi.Announcements;

// AnnouncementFormActivity.java

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appchachi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private Spinner spinnerRecipients;
    private DatabaseReference membersRef;
    private DatabaseReference memberTokensRef;
    private final String[] validRecipients = {"Security", "Medic", "Fire", "All"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_form);

        announcementEditText = findViewById(R.id.et_message);
        Button btnSend = findViewById(R.id.btn_send);

        membersRef = FirebaseDatabase.getInstance().getReference("members");
        memberTokensRef = FirebaseDatabase.getInstance().getReference("memberTokens");

        spinnerRecipients = findViewById(R.id.spinner_recipients);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, validRecipients);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRecipients.setAdapter(adapter);

        btnSend.setOnClickListener(v -> {
            String announcementMessage = announcementEditText.getText().toString().trim();
            String selectedRecipient = spinnerRecipients.getSelectedItem().toString().trim();

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
            // Get the current user's email from Firebase Authentication
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String senderEmail = currentUser.getEmail();

                // Query the "members" node to find the user with the matching email
                DatabaseReference membersRef = FirebaseDatabase.getInstance().getReference("members");
                membersRef.orderByChild("email").equalTo(senderEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Get the first user found with the matching email
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                String senderName = userSnapshot.child("name").getValue(String.class);
                                if (senderName != null) {
                                    // Create the announcement with the retrieved senderName
                                    Announcement newAnnouncement = new Announcement(senderName, announcementMessage, recipients);
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
                                    return; // Exit the loop after finding the user
                                }
                            }
                        } else {
                            Toast.makeText(AnnouncementFormActivity.this, "User with email " + senderEmail + " not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle onCancelled
                        Toast.makeText(AnnouncementFormActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(AnnouncementFormActivity.this, "Current user not authenticated", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AnnouncementFormActivity.this, "Failed to generate announcement key", Toast.LENGTH_SHORT).show();
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
