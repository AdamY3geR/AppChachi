package com.example.appchachi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    ListView lv_security;
    ArrayList<Security> securityList;
    SecurityAdapter securityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        lv_security = findViewById(R.id.lv_security);
    }
}