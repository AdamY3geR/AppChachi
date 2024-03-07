package com.example.appchachi;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;


public class FireFragment extends Fragment {
    ListView lv_fire;
    ArrayList<Fire> fireList;
    FireAdapter fireAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_fire, container, false);
        lv_fire = rootView.findViewById(R.id.lv_fire);
        Fire f1 = new Fire("326900503", "Sami Hacabai", "0545756185", "sami@gmail.com", true, false );
        fireList = new ArrayList<>();
        fireList.add(f1);
        fireAdapter = new FireAdapter(requireContext(),0,0,fireList);

        if (lv_fire != null) {
            lv_fire.setAdapter(fireAdapter);
        }
        return rootView;
    }
}