package com.example.appchachi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    /**
     * Initializes the activity.
     * Sets the content view and delays the start of the MainActivity.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Wait for 2 seconds and then navigate to the login activity
        new Handler().postDelayed(() -> {
            // Create an Intent to start the MainActivity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            // Finish the SplashActivity to prevent it from being shown again
            finish();
        }, 2000); // Delay for 2 seconds
    }
}
