package com.example.mapka.activities;

import android.Manifest;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.mapka.R;
import com.example.mapka.fragments.HistoryFragment;
import com.example.mapka.fragments.MapFragment;
import com.example.mapka.fragments.ShareFragment;
import com.example.mapka.services.LocalizationService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private Intent serviceintent;

    private final BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BATTERY_LOW")) {
                Toast.makeText(context, "Niski poziom baterii. Aplikacja zostaje wyłączona", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();

        this.bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this.navigationItemSelectedListener);
        serviceintent = new Intent(this, LocalizationService.class);
        startService(serviceintent);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.BATTERY_LOW");
        registerReceiver(batteryLevelReceiver, filter);
    }

    public void onDestroy() {
        super.onDestroy();
        stopService(serviceintent);
        unregisterReceiver(batteryLevelReceiver);
    }

    //TODO add view pager to enable swipe functionality between fragments
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectedFragment = null;
            switch(menuItem.getItemId()){
                case R.id.nav_map:
                    selectedFragment = new MapFragment();
                    break;
                case R.id.nav_share:
                    selectedFragment = new ShareFragment();
                    break;
                case R.id.nav_history:
                    selectedFragment = new HistoryFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };

    private void requestPermission() {
        String permission = Manifest.permission.SEND_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

}
