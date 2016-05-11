package com.pruebas.tabs.pruebatabs;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kevin on 09/05/2016.
 */
public class MyService extends Service {
    static final int UPDATE_INTERVAL = 1000;
    private Timer timer = new Timer();

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "Trabajando...", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Iniciando...", Toast.LENGTH_SHORT).show();
        doSomethingRepeatedly();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        Toast.makeText(this, "Terminando...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        return super.bindService(service, conn, flags);
    }

    private void doSomethingRepeatedly() {
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Log.w("Funciona", "SI FNCIONA");
            }

        }, 0, UPDATE_INTERVAL);


    }
}