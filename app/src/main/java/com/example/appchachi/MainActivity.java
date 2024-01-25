package com.example.appchachi;

import android.os.Bundle;
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


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_navigation,fragment);
        fragmentTransaction.commit();
    }
}
