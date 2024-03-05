package com.example.appchachi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.appchachi.NotificationUtils;


public class AnnouncementReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String announcementMessage = intent.getStringExtra("announcementMessage");

        // Trigger notification to inform the user about the new announcement
        NotificationUtils.showNotification(context, "New Announcement", announcementMessage);
    }
}
