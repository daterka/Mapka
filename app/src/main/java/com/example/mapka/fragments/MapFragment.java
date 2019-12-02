package com.example.mapka.fragments;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.mapka.R;
import com.example.mapka.activities.MainActivity;
import com.example.mapka.database.DataBaseAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

import static com.example.mapka.database.DBStrings.DEBUG_TAG;


public class MapFragment extends Fragment {
    private FusedLocationProviderClient fusedLocationClient;
    static final LatLng katInfPK = new LatLng(50.071211, 19.941409);;
    LatLng currentLocation;
    MapView mMapView;
    private GoogleMap googleMap;
    FloatingActionButton floatingActionButton;
    MapFragment thisMapFragment = this;
    DataBaseAdapter dataBaseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        fusedLocationClient.getLastLocation().
                addOnSuccessListener(this.getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                           currentLocation = null;
                        if (location != null) {
                            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                            SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();

                            String locationString = locationToString(location);

                            editor.putString("currentLocation", locationString);
                            editor.apply();

                            mMapView.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap mMap) {
                                    googleMap = mMap;
                                    // For showing a move to my location button
                                    googleMap.setMyLocationEnabled(true);
                                    // For dropping a marker at a point on the Map
                                    googleMap.addMarker(new MarkerOptions().position(katInfPK).title("PK II").snippet("Instytut Informatyki PK."));
//                                    googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Me :)").snippet("This is your current position."));

                                    // For zooming automatically to the location of the marker
                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation).zoom(12).build();
                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                }
                            });
                        }
                    }
                });
        floatingActionButton = rootView.findViewById(R.id.floatingActionButtonSOD);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisMapFragment.fusedLocationClient.getLastLocation().
                        addOnSuccessListener(thisMapFragment.getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                currentLocation = null;
                                if (location != null) {
                                    try {
                                        Geocoder geocoder;
                                        List<Address> addresses;
                                        geocoder = new Geocoder(getContext(), Locale.getDefault());
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

                                        dataBaseAdapter = new DataBaseAdapter(getActivity().getApplicationContext());
                                        dataBaseAdapter.open();
                                        dataBaseAdapter.insertLocalization(dateString, timeString, locationLatitute, locationLongitude, locationName);
                                        dataBaseAdapter.close();
                                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                                        Toast.makeText(getContext(), "Current location saved.", Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        Toast.makeText(getContext(), "DB insertion fail :(.", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }

//                                    Toast.makeText(getContext(), "Current location saved.", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    Toast.makeText(getContext(), "Failed to save current location :(.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private String locationToString(Location location){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
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

            String locationCurrentString = locationName + ", (" + locationLatitute + ", " + locationLongitude + ")";

            Log.d(DEBUG_TAG, "MapFragment : Saving current location to preferences success");

            return locationCurrentString;
        } catch (IOException e) {
            Log.d(DEBUG_TAG, "MapFragment : Saving current location to preferences FAILED !");
            e.printStackTrace();

            return null;
        }


    }


}
