package com.example.mapka.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class LocalizationService extends Service {

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
    }

    private void startService()
    {
        //Wykonanie zadania co 5 min, 60000ms = 1 min
        timer.scheduleAtFixedRate(new mainTask(), 30000, 300000);
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            //TODO Doadanie aktualnej lokalizacji do bazy
            toastHandler.sendEmptyMessage(0);
        }
    }

    public void onDestroy()
    {
        timer.cancel();
        super.onDestroy();
    }

    private final Handler toastHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Toast.makeText(getApplicationContext(), "Aktualna lokalizacja dodana do historii", Toast.LENGTH_SHORT).show();
        }
    };
}
