package com.example.appchachi.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.appchachi.R;
import com.example.appchachi.Security;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SecurityFragment extends Fragment {
    ListView lv_security;
    ArrayList<Security> securityList;
    SecurityAdapter securityAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_security, container, false);
        lv_security = rootView.findViewById(R.id.lv_security);

        // Initialize the list and adapter
        securityList = new ArrayList<>();
        securityAdapter = new SecurityAdapter(requireContext(), 0, 0, securityList);
        lv_security.setAdapter(securityAdapter);

        // Fetch members from Firebase
        fetchMembers();

        return rootView;
    }

    private void fetchMembers() {
        DatabaseReference membersRef = FirebaseDatabase.getInstance().getReference("members");

        membersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                securityList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Security member = snapshot.getValue(Security.class);
                    if (member != null && "Security".equals(member.getMemberType())) {
                        securityList.add(member);
                    }
                }
                securityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
}