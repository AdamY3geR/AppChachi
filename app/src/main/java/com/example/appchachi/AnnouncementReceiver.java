package com.example.appchachi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AnnouncementReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String announcementMessage = intent.getStringExtra("announcementMessage");
    }
}
