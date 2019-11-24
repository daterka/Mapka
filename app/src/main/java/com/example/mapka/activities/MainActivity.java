package com.example.mapka.activities;

import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mapka.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClickMenu(View buttonView){
        if(buttonView == findViewById(R.id.MAP_BUTTON)){
            Toast.makeText(this, "MAP", Toast.LENGTH_SHORT).show();
        }
        else if(buttonView == findViewById(R.id.SHARE_BUTTON)){
            Toast.makeText(this, "SHARE", Toast.LENGTH_SHORT).show();
        }
        else if(buttonView == findViewById(R.id.HISTORY_BUTTON)){
            Toast.makeText(this, "HISTORY", Toast.LENGTH_SHORT).show();
        }
    }
}
