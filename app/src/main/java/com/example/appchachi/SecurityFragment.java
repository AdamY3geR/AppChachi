package com.example.appchachi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class SecurityFragment extends Fragment {
    ListView lv_security;
    ArrayList<Security> securityList;
    SecurityAdapter securityAdapter;
    Security lastSelected;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_security, container, false);
        lv_security= rootView.findViewById(R.id.lv_security);

        // Creating Security objects
        Security s1 = new Security("326900503","Adam Jose Yeger","0537156797","adamyeger@gmail.com",true,true);
        Security s2 = new Security("326900503","Maya Yeger","0533314929","adamyeger@gmail.com",true,false);

        // Adding Security objects to the list
        securityList = new ArrayList<>();
        securityList.add(s1);
        securityList.add(s2);

        // Initializing and setting the adapter for the ListView
        securityAdapter = new SecurityAdapter(requireContext(), 0, 0, securityList);
        if (lv_security != null) {
            lv_security.setAdapter(securityAdapter);
        }
        return rootView;
    }
}
