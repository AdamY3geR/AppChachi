package com.example.appchachi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationUtils {

    // Notification channel ID, name, and description
    private static final String CHANNEL_ID = "my_channel_id";
    private static final String CHANNEL_NAME = "My Notifications";
    private static final String CHANNEL_DESC = "Notifications for important updates";


    /**
     * Creates a notification channel for devices with Android Oreo (API 26) and above.
     *
     * @param context The context of the application.
     */
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Displays a notification with the specified title and message.
     *
     * @param context The context of the application.
     * @param title   The title of the notification.
     * @param message The message content of the notification.
     */
    public static void showNotification(Context context, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_sms_24) // Set notification icon
                .setContentTitle(title) // Set notification title
                .setContentText(message) // Set notification content
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); // Set notification priority

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build()); // Show the notification
    }
}
