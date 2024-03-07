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

public class MedicAdapter extends ArrayAdapter<Medic> {
    private static final int REQUEST_CALL_PHONE_PERMISSION = 1;
    Context context;
    List<Medic> objects;

    /**
     * Constructor for the MedicAdapter.
     *
     * @param context The context of the application.
     * @param resource The resource ID for a layout file containing a TextView to use when instantiating views.
     * @param textViewResourceId The ID of the TextView within the layout resource to be populated.
     * @param objects The list of medical personnel to be displayed.
     */
    public MedicAdapter(Context context, int resource, int textViewResourceId, List<Medic> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context=context;
        this.objects=objects;
    }

    /**
     * Called when the ListView needs a new view to display a medical personnel.
     *
     * @param position The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_layout_members,parent,false);
        TextView tvMemberName = (TextView)view.findViewById(R.id.tv_memberName);
        TextView tvMemberPhone = (TextView)view.findViewById(R.id.tv_memberPhone);
        ImageButton ibtnCall = (ImageButton)view.findViewById(R.id.ibtn_call);
        ImageButton ibtnWhatsapp = (ImageButton)view.findViewById(R.id.ibtn_whatsapp);
        Medic temp = objects.get(position);

        ibtnCall.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the call button is clicked.
             *
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                String phoneNumber = temp.getPhone();
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE_PERMISSION);
                } else {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                    if (context != null)
                        context.startActivity(dialIntent);
                }
            }
        });

        ibtnWhatsapp.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the WhatsApp button is clicked.
             *
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                String phoneNumber = temp.getPhone();
                openWhatsApp(phoneNumber);
            }
        });

        // Set text for member name and phone
        tvMemberName.setText(String.valueOf(temp.getName()));
        tvMemberPhone.setText(String.valueOf(temp.getPhone()));

        return view;
    }


    /**
     * Opens WhatsApp with the provided phone number.
     *
     * @param phoneNumber The phone number to open WhatsApp with.
     */
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
}