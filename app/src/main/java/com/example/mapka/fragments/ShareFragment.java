package com.example.mapka.fragments;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.mapka.R;
import com.example.mapka.activities.MainActivity;

public class ShareFragment extends Fragment {

    private EditText mobileno;
    private Button sendsms;
    private static final int REQUEST_PICK_CONTACT = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_share, container, false);

        mobileno = (EditText) view.findViewById(R.id.number_text);
        sendsms=(Button) view.findViewById(R.id.send_button);

        sendsms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO zastapic message lokalizacja
                String message="Localization";
                try{
                    SmsManager smsmgr = SmsManager.getDefault();
                    smsmgr.sendTextMessage(mobileno.getText().toString(),null, message,null,null);
                    Toast.makeText(getActivity(), "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.contact_picker).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_CONTACT);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_PICK_CONTACT) {
            String phoneNo = null;

            Uri uri = data.getData();
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);

            if (cursor.moveToFirst()) {
                int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                phoneNo = cursor.getString(phoneIndex);

                mobileno.setText(phoneNo);
            }
            cursor.close();
        }
    }
}
