package com.example.appchachi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class AnnouncementService extends Service {
    // Implementation of onStartCommand() and other methods as needed

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Perform any background tasks related to the announcement
        return super.onStartCommand(intent, flags, startId);
    }
}

