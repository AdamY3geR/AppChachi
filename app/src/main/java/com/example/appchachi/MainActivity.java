package com.example.appchachi;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.navi_dashboard:
                        selectedFragment = new DashboardFragment();
                        break;
                    case R.id.navi_messages:
                        selectedFragment = new MesseagesFragment();
                        break;
                    case R.id.navi_calendar:
                        selectedFragment = new CalendarFragment();
                        break;
                    case R.id.navi_chachi_group:
                        selectedFragment = new ChachiGroupFragment();
                        break;
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, selectedFragment);
                transaction.commit();
                return true;
            }
        });
        // Set the initial fragment
        bottomNavigationView.setSelectedItemId(R.id.navi_dashboard);
    }
}
