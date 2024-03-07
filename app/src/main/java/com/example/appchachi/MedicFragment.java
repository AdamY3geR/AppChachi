package com.example.appchachi;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;


public class MedicFragment extends Fragment {
    ListView lv_medic;
    ArrayList<Medic> medicList;
    MedicAdapter medicAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_medic, container, false);
        lv_medic= rootView.findViewById(R.id.lv_medic);
        Medic m1 = new Medic("326900503", "Rofe Tov", "0506590083", "Yeger180@gmail.com", true, true);
        medicList = new ArrayList<>();
        medicList.add(m1);
        medicAdapter = new MedicAdapter(requireContext(),0,0,medicList);

        if (lv_medic != null) {
            lv_medic.setAdapter(medicAdapter);
        }
        return rootView;
    }
}