package com.example.mapka.activities;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.example.mapka.R;
import com.example.mapka.fragments.HistoryFragment;
import com.example.mapka.fragments.MapFragment;
import com.example.mapka.fragments.ShareFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();

        this.bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this.navigationItemSelectedListener);

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

}
