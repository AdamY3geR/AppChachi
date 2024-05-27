/**
 * Fragment class for displaying medical personnel information.
 */
package com.example.appchachi.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.appchachi.Medic;
import com.example.appchachi.R;

import java.util.ArrayList;


public class MedicFragment extends Fragment {

    // ListView for displaying medical personnel
    ListView lv_medic;

    // List of medical personnel
    ArrayList<Medic> medicList;

    // Adapter for medical personnel ListView
    MedicAdapter medicAdapter;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_medic, container, false);

        // Initialize ListView
        lv_medic= rootView.findViewById(R.id.lv_medic);

        // Create a sample Medic object
        Medic m1 = new Medic("326900503", "Rofe Tov", "0506590083", "Yeger180@gmail.com", "medic");

        // Initialize the list of medical personnel and add the sample Medic object
        medicList = new ArrayList<>();
        medicList.add(m1);

        // Initialize the MedicAdapter
        medicAdapter = new MedicAdapter(requireContext(),0,0,medicList);

        // Set the adapter to the ListView
        if (lv_medic != null) {
            lv_medic.setAdapter(medicAdapter);
        }

        return rootView;
    }
}
