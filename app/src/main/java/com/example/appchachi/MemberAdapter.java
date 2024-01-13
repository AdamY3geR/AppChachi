package com.example.appchachi;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class MemberAdapter extends ArrayAdapter {
    public MemberAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List objects) {
        super(context, resource, textViewResourceId, objects);
    }
    Context context;
    List<Member> objects;

}
