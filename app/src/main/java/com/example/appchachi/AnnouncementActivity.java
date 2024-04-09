package com.example.appchachi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for displaying announcements and creating new announcements.
 */
public class AnnouncementActivity extends AppCompatActivity {

    private AnnouncementAdapter adapter;
    private List<String> announcements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        // Button to create a new announcement
        Button btnCreateAnnouncement = findViewById(R.id.btn_create_announcement);
        btnCreateAnnouncement.setOnClickListener(v -> openAnnouncementFormActivity());

        // Initialize RecyclerView and layout manager
        RecyclerView recyclerView = findViewById(R.id.rv_announcements);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize announcements list and adapter
        announcements = new ArrayList<>();
        adapter = new AnnouncementAdapter(announcements);

        // Set adapter to RecyclerView
        recyclerView.setAdapter(adapter);

        // Populate announcements from Firebase Realtime Database
        populateAnnouncements();
    }

    /**
     * Fetches announcements from Firebase Realtime Database and populates the RecyclerView.
     */
    private void populateAnnouncements() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("announcements");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                announcements.clear();

                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = auth.getCurrentUser();

                if (currentUser != null) {
                    String currentUserId = currentUser.getUid(); // Assuming you're using Firebase Authentication

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Retrieve announcement data from snapshot
                        String message = snapshot.child("message").getValue(String.class);
                        List<String> recipients = new ArrayList<>();

                        // Retrieve recipients list from snapshot
                        DataSnapshot recipientsSnapshot = snapshot.child("recipients");
                        for (DataSnapshot recipientSnapshot : recipientsSnapshot.getChildren()) {
                            String recipientId = recipientSnapshot.getKey();
                            recipients.add(recipientId);
                        }

                        // Check if the current user is a recipient
                        if (recipients.contains(currentUserId)) {
                            announcements.add(message);
                            // Send notification for this announcement
                            NotificationUtils.showNotification(AnnouncementActivity.this, "New Announcement", message);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }


    /**
     * Opens the AnnouncementFormActivity to create a new announcement.
     */
    private void openAnnouncementFormActivity() {
        Intent intent = new Intent(this, AnnouncementFormActivity.class);
        startActivity(intent);
    }
}