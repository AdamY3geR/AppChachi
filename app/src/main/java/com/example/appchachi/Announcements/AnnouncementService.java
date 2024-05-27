package com.example.appchachi.Announcements;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.appchachi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnnouncementService extends Service {

    private static final String TAG = "AnnouncementService";
    private static final String CHANNEL_ID = "announcement_channel";
    private static final int CHECK_INTERVAL = 1000; // Check every 1 second
    private Handler handler;
    private Runnable runnable;
    private DatabaseReference announcementsRef;
    private DatabaseReference userRef;
    private String currentUserMemberType;
    private long lastCheckedTime;
    private SharedPreferences sharedPreferences;
    private Set<String> processedAnnouncementIds;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        announcementsRef = FirebaseDatabase.getInstance().getReference("announcements");
        userRef = FirebaseDatabase.getInstance().getReference("members");
        lastCheckedTime = System.currentTimeMillis();
        sharedPreferences = getSharedPreferences("com.example.appchachi.loginup.PREFS", MODE_PRIVATE);
        processedAnnouncementIds = new HashSet<>();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (currentUserMemberType == null) {
                        Log.d(TAG, "Member type is null, fetching...");
                        getCurrentUserMemberType();
                    } else {
                        Log.d(TAG, "Checking for new announcements...");
                        checkForNewAnnouncements();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error in runnable: " + e.getMessage(), e);
                }
                handler.postDelayed(this, CHECK_INTERVAL);
            }
        };
        handler.post(runnable);
        createNotificationChannel();
        getCurrentUserMemberType();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        Log.d(TAG, "Service destroyed");
    }

    private void getCurrentUserMemberType() {
        String lastUserId = sharedPreferences.getString("last_user_id", null);
        if (lastUserId != null) {
            Log.d(TAG, "Last user ID found: " + lastUserId);
            userRef.child(lastUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    currentUserMemberType = dataSnapshot.child("memberType").getValue(String.class);
                    Log.d(TAG, "Current user member type: " + currentUserMemberType);
                    // Check if currentUserMemberType is not null, then proceed to check for new announcements
                    if (currentUserMemberType != null) {
                        checkForNewAnnouncements();
                    } else {
                        Log.e(TAG, "Current user member type is null");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG, "Failed to get user member type: " + databaseError.getMessage());
                }
            });
        } else {
            Log.e(TAG, "No last user ID found in shared preferences");
        }
    }


    private void checkForNewAnnouncements() {
        announcementsRef.orderByChild("timestamp").startAt(lastCheckedTime).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Announcement> newAnnouncements = new ArrayList<>();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String currentUserEmail = currentUser != null ? currentUser.getEmail() : "";
                Log.d(TAG, "Checking announcements for user: " + currentUserEmail);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String announcementId = snapshot.getKey();
                    if (announcementId != null && !processedAnnouncementIds.contains(announcementId)) {
                        Announcement announcement = snapshot.getValue(Announcement.class);
                        if (announcement != null && announcement.getRecipients() != null &&
                                (announcement.getRecipients().contains("All") || announcement.getRecipients().contains(currentUserMemberType))) {
                            if (announcement.getFrom() != null) {
                                checkSenderEmail(announcement, currentUserEmail, newAnnouncements, announcementId);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }

    private void checkSenderEmail(Announcement announcement, String currentUserEmail, List<Announcement> newAnnouncements, String announcementId) {
        userRef.orderByChild("name").equalTo(announcement.getFrom()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isSenderCurrentUser = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String senderEmail = snapshot.child("email").getValue(String.class);
                    if (senderEmail != null && senderEmail.equals(currentUserEmail)) {
                        isSenderCurrentUser = true;
                        break;
                    }
                }
                if (!isSenderCurrentUser) {
                    newAnnouncements.add(announcement);
                }
                processedAnnouncementIds.add(announcementId);

                if (!newAnnouncements.isEmpty()) {
                    showNotification(newAnnouncements);
                    lastCheckedTime = System.currentTimeMillis();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to get sender email: " + databaseError.getMessage());
            }
        });
    }

    private void showNotification(List<Announcement> newAnnouncements) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            Log.e(TAG, "NotificationManager is null");
            return;
        }

        for (Announcement announcement : newAnnouncements) {
            Intent intent = new Intent(this, AnnouncementActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Ensures a new instance isn't created
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.baseline_security_24) // Use your actual icon resource
                    .setContentTitle("New Announcement")
                    .setContentText(announcement.getMessage())
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            try {
                Log.d(TAG, "Showing notification for: " + announcement.getMessage());
                notificationManager.notify((int) (Math.random() * 10000), builder.build());
            } catch (Exception e) {
                Log.e(TAG, "Failed to show notification: " + e.getMessage(), e);
            }
        }
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Announcement Channel";
            String description = "Channel for announcement notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            } else {
                Log.e(TAG, "Failed to create notification channel: NotificationManager is null");
            }
        }
    }
}
