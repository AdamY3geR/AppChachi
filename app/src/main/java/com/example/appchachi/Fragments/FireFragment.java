package com.example.appchachi.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appchachi.R;
import com.example.appchachi.Fire;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireFragment extends Fragment {
    private static final String TAG = "FireFragment";
    ListView lv_fire;
    ArrayList<Fire> fireList;
    FireAdapter fireAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fire, container, false);
        lv_fire = rootView.findViewById(R.id.lv_fire);
        fireList = new ArrayList<>();
        fireAdapter = new FireAdapter(requireContext(), 0, 0, fireList);

        if (lv_fire != null) {
            lv_fire.setAdapter(fireAdapter);
        }

        fetchFireMembers();
        return rootView;
    }

    private void fetchFireMembers() {
        FirebaseDatabase.getInstance().getReference("members")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        fireList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Fire member = snapshot.getValue(Fire.class);
                            if (member != null) {
                                Log.d(TAG, "Retrieved member: " + member.getName() + ", type: " + member.getMemberType());
                                if ("Fire".equals(member.getMemberType())) {
                                    fireList.add(member);
                                }
                            } else {
                                Log.w(TAG, "Null member object retrieved from database");
                            }
                        }
                        fireAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "Database error", databaseError.toException());
                    }
                });
    }
}
