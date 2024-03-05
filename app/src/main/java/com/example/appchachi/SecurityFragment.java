package com.example.appchachi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class SecurityFragment extends Fragment {
    ListView lv_security;
    ArrayList<Security> securityList;
    SecurityAdapter securityAdapter;
    Security lastSelected;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_security, container, false);
        lv_security= rootView.findViewById(R.id.lv_security);
        Security s1= new Security("326900503","Adam Jose Yeger","0537156797","adamyeger@gmail.com","car patrol",true,"BetLehem MainGate");
        Security s2= new Security("326900503","Maya Yeger","0533314929","adamyeger@gmail.com","car patrol",true,"BetLehem MainGate");

        securityList = new ArrayList<>();
        securityList.add(s1);
        securityList.add(s2);
        securityAdapter = new SecurityAdapter(requireContext(),0,0,securityList);

        if (lv_security != null) {
            lv_security.setAdapter(securityAdapter);
        }
        return rootView;
    }



}
