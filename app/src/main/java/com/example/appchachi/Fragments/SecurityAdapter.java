/**
 * ArrayAdapter for displaying security information in a ListView.
 */
package com.example.appchachi.Fragments;

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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import com.example.appchachi.R;
import com.example.appchachi.Security;

import java.util.List;

public class SecurityAdapter extends ArrayAdapter<Security> {

    // Request code for CALL_PHONE permission
    private static final int REQUEST_CALL_PHONE_PERMISSION = 1;

    // Context and list of Security objects
    Context context;
    List<Security> objects;

    /**
     * Constructor for SecurityAdapter.
     *
     * @param context The context of the application.
     * @param resource The resource ID for a layout file containing a layout to use when instantiating views.
     * @param textViewResourceId The resource ID for a TextView in the layout resource to represent the Security objects.
     * @param objects The list of Security objects to represent in the ListView.
     */
    public SecurityAdapter(Context context, int resource, int textViewResourceId, List<Security> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_layout_members, parent, false);
        TextView tvMemberName = (TextView) view.findViewById(R.id.tv_memberName);
        TextView tvMemberPhone = (TextView) view.findViewById(R.id.tv_memberPhone);
        ImageButton ibtnCall = (ImageButton) view.findViewById(R.id.ibtn_call);
        ImageButton ibtnWhatsapp = (ImageButton) view.findViewById(R.id.ibtn_whatsapp);
        Security temp = objects.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_layout_members, parent, false);
        }

        convertView.setClickable(true);
        convertView.setFocusable(true);

        ibtnCall.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click event for the call button.
             *
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                // Get the phone number of the current Security object
                String phoneNumber = temp.getPhone();

                // Check if CALL_PHONE permission is granted
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Request CALL_PHONE permission if not granted
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE_PERMISSION);
                } else {
                    // If permission is granted, create an intent to dial the phone number
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                    if (context != null)
                        // Start the intent to initiate a phone call
                        context.startActivity(dialIntent);
                }
            }
        });

        ibtnWhatsapp.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click event for the WhatsApp button.
             *
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                // Get the phone number of the current Security object
                String phoneNumber = temp.getPhone();

                // Call the openWhatsApp method with the phone number to open WhatsApp
                openWhatsApp(phoneNumber);
            }
        });

        tvMemberName.setText(String.valueOf(temp.getName()));
        tvMemberPhone.setText(String.valueOf(temp.getPhone()));

        return view;
    }

    /**
     * Opens WhatsApp with the specified phone number.
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

    /**
     * Returns the item ID for the specified position in the ListView.
     *
     * @param position The position of the item within the adapter's data set.
     * @return The item ID for the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }
}
