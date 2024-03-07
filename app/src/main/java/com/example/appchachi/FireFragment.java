package com.example.appchachi;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Fragment class responsible for displaying fire department information.
 */
public class FireFragment extends Fragment {
    ListView lv_fire;
    ArrayList<Fire> fireList;
    FireAdapter fireAdapter;


    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fire, container, false);
        lv_fire = rootView.findViewById(R.id.lv_fire);
        Fire f1 = new Fire("326900503", "Sami Hacabai", "0545756185", "sami@gmail.com", true, false );
        fireList = new ArrayList<>();
        fireList.add(f1);
        fireAdapter = new FireAdapter(requireContext(), 0, 0, fireList);

        if (lv_fire != null) {
            lv_fire.setAdapter(fireAdapter);
        }
        return rootView;
    }

}