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
import com.example.appchachi.Medic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MedicFragment extends Fragment {
    private static final String TAG = "MedicFragment";
    ListView lv_medic;
    ArrayList<Medic> medicList;
    MedicAdapter medicAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_medic, container, false);
        lv_medic = rootView.findViewById(R.id.lv_medic);
        medicList = new ArrayList<>();
        medicAdapter = new MedicAdapter(requireContext(), 0, 0, medicList);

        if (lv_medic != null) {
            lv_medic.setAdapter(medicAdapter);
        }

        fetchMedicMembers();
        return rootView;
    }

    private void fetchMedicMembers() {
        FirebaseDatabase.getInstance().getReference("members")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        medicList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Medic member = snapshot.getValue(Medic.class);
                            if (member != null) {
                                Log.d(TAG, "Retrieved member: " + member.getName() + ", type: " + member.getMemberType());
                                if ("Medic".equals(member.getMemberType())) {
                                    medicList.add(member);
                                }
                            } else {
                                Log.w(TAG, "Null member object retrieved from database");
                            }
                        }
                        medicAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "Database error", databaseError.toException());
                    }
                });
    }
}
