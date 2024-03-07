package com.example.appchachi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appchachi.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;

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



        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navi_security) {
                replaceFragment(new SecurityFragment());
            } else if (item.getItemId() == R.id.navi_medic) {
                replaceFragment(new MedicFragment());
            } else if (item.getItemId() == R.id.navi_fire) {
                replaceFragment(new FireFragment());
            } else if (item.getItemId() == R.id.navi_map) {
                replaceFragment(new MapFragment());
            }
            return true;
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        return super.onOptionsItemSelected(item);
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
