package com.example.mapka.activities;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mapka.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void onButtonClickStart(View view){
        Intent startIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(startIntent);
    }
}
