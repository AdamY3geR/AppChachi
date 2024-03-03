package com.example.appchachi;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.util.List;

public class SecurityAdapter extends ArrayAdapter<Security> {
    private static final int REQUEST_CALL_PHONE_PERMISSION = 1;
    Context context;
    List<Security> objects;
    public SecurityAdapter(Context context, int resource, int textViewResourceId, List<Security> objects) {
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
        ImageButton ibtnWhatsapp = (ImageButton)view.findViewById(R.id.ibtn_whatsapp);
        Security temp = objects.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_layout_members, parent, false);
        }
        convertView.setClickable(true);
        convertView.setFocusable(true);

        ibtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = temp.getPhone();
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE_PERMISSION);
                }
                else{
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                if (context != null)
                    context.startActivity(dialIntent);
                }
            }
        });

        ibtnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = temp.getPhone();
                openWhatsApp(phoneNumber);
            }
        });


        tvMemberName.setText(String.valueOf(temp.getName()));
        tvMemberPhone.setText(String.valueOf(temp.getPhone()));


        return view;
    }
    private void openWhatsApp(String phoneNumber) {
        // Use the correct action for opening WhatsApp
        Intent sendIntent = new Intent("android.intent.action.SENDTO");
        sendIntent.setData(Uri.parse("smsto:" + phoneNumber));

        // Specify the package for WhatsApp
        sendIntent.setPackage("com.whatsapp");

        // Start the intent.
        if (context != null) {
            context.startActivity(sendIntent);
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
}
