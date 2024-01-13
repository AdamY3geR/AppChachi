package com.example.appchachi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.example.appchachi.R;
import com.example.appchachi.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navi_messages) {
                Intent intent = new Intent(MainActivity.this, MesseagesFragment.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.navi_chachi_group) {
                Intent intent = new Intent(MainActivity.this, ChachiGroupFragment.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.navi_calendar) {
                Intent intent = new Intent(MainActivity.this, CalendarFragment.class);
                startActivity(intent);
            }
            return true;
        });

    }
}
