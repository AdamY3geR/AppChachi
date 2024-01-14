package com.example.appchachi;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class MemberAdapter extends ArrayAdapter<Member> {
    Context context;
    List<Member> objects;
    public MemberAdapter(Context context, int resource, int textViewResourceId, List<Member> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context=context;
        this.objects=objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_layout_members,parent,false);
        TextView tvMemberName = (TextView)view.findViewById(R.id.tv_memberName);
        TextView tvMemberPhone = (TextView)view.findViewById(R.id.tv_memberPhone);
        ImageButton ibtnCall = (ImageButton)view.findViewById(R.id.ibtn_call);
        ImageButton ibtnSms = (ImageButton)view.findViewById(R.id.ibtn_sms);
        Member temp = objects.get(position);


        tvMemberName.setText(String.valueOf(temp.getName()));
        tvMemberPhone.setText(temp.getPhone());

        return view;
    }
}
