package com.example.appchachi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.appchachi.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new SecurityFragment());

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_announcement) {
            sendAnnouncement();
            return true;
            // Add more cases for other items in the future
        }
        return super.onOptionsItemSelected(item);
    }


    private void sendAnnouncement() {
        // You can replace this with your announcement message
        String announcementMessage = "This is an important announcement!";

        // Broadcast the announcement message
        Intent broadcastIntent = new Intent("com.example.appchachi.ANNOUNCEMENT_ACTION");
        broadcastIntent.putExtra("announcementMessage", announcementMessage);
        sendBroadcast(broadcastIntent);

        // Start the service if needed
        startService(new Intent(this, AnnouncementService.class));
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_navigation,fragment);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
