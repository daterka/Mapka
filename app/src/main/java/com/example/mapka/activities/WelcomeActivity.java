package com.example.mapka.activities;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mapka.R;

public class WelcomeActivity extends AppCompatActivity {
    Switch switch_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setLanguage();


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
        finish();
    }
}
