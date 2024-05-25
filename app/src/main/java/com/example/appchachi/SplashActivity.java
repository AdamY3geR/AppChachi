package com.example.appchachi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.appchachi.loginup.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;

    /**
     * Initializes the activity.
     * Sets the content view and delays the start of the LoginActivity.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Wait for 2 seconds and then request notification permission
        new Handler().postDelayed(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Check for Android 13 and above
                requestNotificationPermission();
            } else {
                // If below Android 13, proceed to login activity directly
                navigateToLoginActivity();
            }
        }, 2000); // Delay for 2 seconds
    }

    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            // Request notification permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    REQUEST_NOTIFICATION_PERMISSION);
        } else {
            // Permission already granted, proceed to login activity
            navigateToLoginActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
            navigateToLoginActivity();
        }
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
