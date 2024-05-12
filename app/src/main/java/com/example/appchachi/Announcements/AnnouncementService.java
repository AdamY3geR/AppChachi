package com.example.appchachi.Announcements;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Service class for handling announcements.
 */
public class AnnouncementService extends Service {

    /**
     * Called when the service is initially created.
     *
     * @param intent The Intent that was used to bind to this service, as given to Context.bindService.
     * @return The IBinder object that this service returns when its onBind method is called.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Called by the system every time a client explicitly starts the service by calling startService(Intent).
     *
     * @param intent The Intent supplied to startService(Intent), as given.
     * @param flags Additional data about this start request.
     * @param startId A unique integer representing this specific request to start.
     * @return A value indicating what semantics the system should use for the service's current started state.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Perform any background tasks related to the announcement
        return super.onStartCommand(intent, flags, startId);
    }
}
