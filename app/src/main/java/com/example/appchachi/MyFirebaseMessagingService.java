package com.example.appchachi;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Check if the message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

            // Handle the notification message.
            // You can display the notification, update UI, etc.
            // For example, you can use NotificationCompat to create and display a notification:
            // NotificationUtils.displayNotification(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

        // Check if the message contains data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Data Payload: " + remoteMessage.getData());

            // Handle the data payload.
            // You can process the data in your app as needed.
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        // If you need to handle the generation of a new registration token,
        // override this method.
        // This method is called whenever a new token is generated.
        // You can save the token to your server or perform other tasks as needed.
    }
}
