package com.example.appchachi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MemberAdapter extends ArrayAdapter {
    Context context;
    List<Member> objects;
    public MemberAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Member> objects) {
        super(context, resource, textViewResourceId, objects);
    }

}
