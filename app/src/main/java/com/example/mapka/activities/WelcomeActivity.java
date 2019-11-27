package com.example.mapka.activities;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.example.mapka.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class WelcomeActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng katInfPK;
    Switch switch_button;
    private static  final int REQUEST_LOCATION = 999;
    private static final int REQUEST_INTERNET= 888;
    private static final int REQUEST_SEND_SMS= 777;
    private static final int REQUEST_READ_CONTACTS= 666;
    private static final int MY_APP_PERMISSIONS_REQUEST= 111;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setLanguage();

        handlePermissions();


    }


    public void handlePermissions(){

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PERMISSION_GRANTED){
             /*
            Requesting the Location permission
            1st Param - Activity
            2nd Param - String Array of permissions requested
            3rd Param -Unique Request code. Used to identify these set of requested permission
            */
            ActivityCompat.requestPermissions(this, new String[] {
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
            }, REQUEST_LOCATION);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.INTERNET
            }, REQUEST_INTERNET);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.SEND_SMS
            }, REQUEST_SEND_SMS);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.READ_CONTACTS
            }, REQUEST_READ_CONTACTS);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    //Permission Granted
                } else
                    Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
                break;

            case REQUEST_INTERNET:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    //Permission Granted
                } else
                    Toast.makeText(this, "INTERNET Permission Denied", Toast.LENGTH_SHORT).show();
                break;

            case REQUEST_SEND_SMS:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    //Permission Granted
                } else
                    Toast.makeText(this, "SEND SMS Permission Denied", Toast.LENGTH_SHORT).show();
                break;

            case REQUEST_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    //Permission Granted
                } else
                    Toast.makeText(this, "READ CONTACTS Permission Denied", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public void setLanguage(){
        this.switch_button = (Switch) findViewById(R.id.pl_eng_switch);
        this.switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(WelcomeActivity.this, "Wybrano język polski.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(WelcomeActivity.this, "English choosen.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onButtonClickStart(View view){
        Intent startIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(startIntent);
    }
}
