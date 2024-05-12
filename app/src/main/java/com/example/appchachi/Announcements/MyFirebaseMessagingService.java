package com.example.appchachi.Announcements;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Check if the message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

            // Handle the notification message.
            // You can display the notification, update UI, etc.
            // For example, you can use NotificationUtils to create and display a notification:
            NotificationUtils.showNotification(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

        // Check if the message contains data payload.
        if (!remoteMessage.getData().isEmpty()) {
            Log.d(TAG, "Data Payload: " + remoteMessage.getData());

            // Handle the data payload.
            // You can process the data in your app as needed.
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        // Get the current user's ID from Firebase Authentication
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            // Save the FCM token to the database using the user's UID
            saveTokenToDatabase(userId, token);
        }
    }

    private void saveTokenToDatabase(String userId, String token) {
        DatabaseReference tokensRef = FirebaseDatabase.getInstance().getReference("user_tokens");
        tokensRef.child(userId).setValue(token)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Token saved to database"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to save token to database: " + e.getMessage()));
    }
}
