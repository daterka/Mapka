package com.example.mapka;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;
import com.example.mapka.services.LocalizationService;

public class DefaultApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, LocalizationService.class));
    }
}
