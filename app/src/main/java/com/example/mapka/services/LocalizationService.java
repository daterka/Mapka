package com.example.mapka.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import com.example.mapka.database.DataBaseAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LocalizationService extends Service {
    LatLng currentLocation;
    FusedLocationProviderClient fusedLocationClient;
    DataBaseAdapter dataBaseAdapter;

    private static Timer timer = new Timer();
    private Context ctx;

    public LocalizationService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate()
    {
        super.onCreate();
        ctx = this;
        startService();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void startService()
    {
        //Wykonanie zadania co 5 min, 60000ms = 1 min
//        timer.scheduleAtFixedRate(new mainTask(), 30000, 15000);
        timer.scheduleAtFixedRate(new mainTask(), 30000, 600000);
    }

    private class mainTask extends TimerTask
    {
        @RequiresApi(api = Build.VERSION_CODES.P)
        public void run()
        {
            //TODO Doadanie aktualnej lokalizacji do bazy

            getCurrentLocation();

//            toastHandler.sendEmptyMessage(0);
        }
    }

    public void onDestroy()
    {
        timer.cancel();
        super.onDestroy();
    }

//    private final Handler toastHandler = new Handler()
//    {
//        @Override
//        public void handleMessage(Message msg)
//        {
//            Toast.makeText(getApplicationContext(), "Aktualna lokalizacja dodana do historii", Toast.LENGTH_SHORT).show();
//        }
//    };

    public void getCurrentLocation(){
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                currentLocation = null;
                if (location != null) {
                    try {
                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(ctx, Locale.getDefault());
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();

                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = formatter.format(date).toString();

                        formatter = new SimpleDateFormat("HH:mm:ss");
                        String timeString = formatter.format(date).toString();

                        String locationName = city + ", " + address;
                        String locationLatitute = String.format("%f", location.getLatitude());
                        String locationLongitude = String.format("%f", location.getLongitude());

                        dataBaseAdapter = new DataBaseAdapter(ctx.getApplicationContext());
                        dataBaseAdapter.open();
                        dataBaseAdapter.insertLocalization(dateString, timeString, locationLatitute, locationLongitude, locationName);
                        dataBaseAdapter.close();
                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                        String locationString = locationName + " (" + locationLatitute + ", " + locationLongitude + ")";
                        SharedPreferences pref = ctx.getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("currentLocation", locationString);
                        editor.apply();


                        Toast.makeText(ctx, "SERVICE : Current location saved.", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(ctx, "SERVICE : DB insertion fail :(.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

//                                    Toast.makeText(getContext(), "Current location saved.", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(ctx, "SERVICE : Failed to save current location :(.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
