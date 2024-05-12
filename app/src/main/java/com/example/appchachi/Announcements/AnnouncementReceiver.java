package com.example.appchachi.Announcements;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * BroadcastReceiver implementation for handling announcements.
 */
public class AnnouncementReceiver extends BroadcastReceiver {

    /**
     * Called when a broadcast is received.
     *
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String announcementMessage = intent.getStringExtra("announcementMessage");

        // Trigger notification to inform the user about the new announcement
        NotificationUtils.showNotification(context, "New Announcement", announcementMessage);
    }
}
