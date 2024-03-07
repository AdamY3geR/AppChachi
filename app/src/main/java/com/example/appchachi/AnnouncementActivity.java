package com.example.appchachi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private RecyclerView recyclerView;
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
        recyclerView = findViewById(R.id.rv_announcements);
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String announcement = snapshot.getValue(String.class);
                    announcements.add(announcement);
                }
                // Notify adapter that data set has changed
                adapter.notifyDataSetChanged();
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
