package com.example.appchachi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appchachi.Announcements.AnnouncementActivity;
import com.example.appchachi.Announcements.AnnouncementService;
import com.example.appchachi.Fragments.FireFragment;
import com.example.appchachi.Fragments.MedicFragment;
import com.example.appchachi.Fragments.SecurityFragment;
import com.example.appchachi.databinding.ActivityMainBinding;
import com.example.appchachi.loginup.LoginActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Main activity of the application responsible for managing fragments and navigation.
 */
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new SecurityFragment());

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Start the AnnouncementService
        Intent serviceIntent = new Intent(this, AnnouncementService.class);
        startService(serviceIntent);

        String memberType = getIntent().getStringExtra("MEMBER_TYPE");
        if (memberType != null) {
            // Set the bottom navigation item based on the member type
            switch (memberType) {
                case "Security":
                    binding.bottomNavigation.setSelectedItemId(R.id.navi_security);
                    replaceFragment(new SecurityFragment());
                    break;
                case "Medic":
                    binding.bottomNavigation.setSelectedItemId(R.id.navi_medic);
                    replaceFragment(new MedicFragment());
                    break;
                case "Fire":
                    binding.bottomNavigation.setSelectedItemId(R.id.navi_fire);
                    replaceFragment(new FireFragment());
                    break;
            }
        } else {
            // Default to SecurityFragment if no member type is passed
            replaceFragment(new SecurityFragment());
        }

        // Set up bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment fragment = null;
            if (itemId == R.id.navi_security) {
                fragment = new SecurityFragment();
            } else if (itemId == R.id.navi_medic) {
                fragment = new MedicFragment();
            } else if (itemId == R.id.navi_fire) {
                fragment = new FireFragment();
            }

            // Replace the current fragment with the selected fragment
            if (fragment != null) {
                replaceFragment(fragment);
            }

            return true;
        });

        setSupportActionBar(findViewById(R.id.toolbar)); // Set the toolbar
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which items are placed.
     * @return You must return true for the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in the options menu is selected.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_announcement) {
            Intent intent = new Intent(this, AnnouncementActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.action_logout) {
            // Call the logout method
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Logout method.
     */
    private void logout() {
        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut();

        // Redirect to LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Replaces the current fragment with the given fragment.
     *
     * @param fragment The fragment to replace.
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_navigation, fragment);
        fragmentTransaction.commit();
    }
}
