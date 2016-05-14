package com.pruebas.tabs.pruebatabs;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.WifiConfiguration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.*;
import android.provider.Settings;
import android.provider.Settings.System;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pruebas.tabs.pruebatabs.Adapters.ComentaryAdapter;
import com.pruebas.tabs.pruebatabs.Model.Comentary;
import com.pruebas.tabs.pruebatabs.Model.Publication;
import com.pruebas.tabs.pruebatabs.Model.User;
import com.pruebas.tabs.pruebatabs.Net.Net;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kevin on 04/05/2016.
 */
public class Publicacion extends Activity{
    ListView listView;
    private int UserPublited;
    List<Comentary> userCommented;
    Net net;
    ComentaryAdapter comentaryAdapter;
    boolean builded = false;


    float last_x =0;float x;
    float last_y=0;float y;
    float last_z=0;float  z;

    WindowManager.LayoutParams layoutParams;
    SensorManager sensorManager;
    Sensor Msensor;

    private int brightness;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window
    private Window window;


    TextView MiNombre;
    EditText MiTexto;
    Button sendMi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences("_PREFERENCES_", 0);
        int theme = settings.getInt("colorTheme", 0);
        if (theme==0){
            setTheme(R.style.Theme_Cvshare);
        }
        else{
            setTheme(R.style.Theme_Foodstyle);
        }
        setContentView(R.layout.activity_publicacion_anadir);

        layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness=0.1f;
        getWindow().setAttributes(layoutParams);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Msensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        ///////////////////////////////////////////////777
        cResolver = getContentResolver();

        //Get the current window
        window = getWindow();

        try
        {
            //Get the current system brightness
            brightness = System.getInt(cResolver, System.SCREEN_BRIGHTNESS);
        }
        catch (Settings.SettingNotFoundException e)
        {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }


        ////////////////////////////////////////////////77

        TextView ContactoNombre = (TextView) findViewById(R.id.ContactoNombre);
        TextView ContactoPuesto = (TextView) findViewById(R.id.ContactoPuesto);
        ImageView img = (ImageView) findViewById(R.id.FotoContactoNombre);
        listView = (ListView)findViewById(R.id.ContactoComentarios);
        MiNombre = (TextView) findViewById(R.id.myNameUser);
        MiTexto = (EditText) findViewById(R.id.myComentaryUsert);
        sendMi = (Button) findViewById(R.id.mySendComentary);
        userCommented= new ArrayList<>();

        Intent intent = getIntent();
        UserPublited = intent.getIntExtra("USER",0);
        if (UserPublited!=0) {
            ContactoNombre.setText(intent.getStringExtra("USERname"));
            ContactoPuesto.setText(intent.getStringExtra("USERpuesto"));
            net = new Net(Publicacion.this);
            net.execute("loadCometarios",UserPublited);

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        comentaryAdapter = new ComentaryAdapter(userCommented);
        listView.setAdapter(comentaryAdapter);

        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        sendMi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                net = new Net(Publicacion.this);
                MiTexto.setText("");
                net.execute("writeComment", Main.MyUser.getMid(), UserPublited,MiTexto.getText().toString());
                net = new Net(Publicacion.this);
                net.execute("loadCometarios", UserPublited);
            }
        });
//        lista.setWeightSum();

        //  ContactoNombre.setText(savedInstanceState.getString("USER", "NOP"));
/*        inicioSesion = (Button) findViewById(R.id.InicioSesionBtn);
        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioSesion.this, Main.class);
                startActivity(intent);
            }
        });
  */
    }

    SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //
            float mx = event.values[0];
            mx*=20;

                    x = event.values[SensorManager.DATA_X];
                    y = event.values[SensorManager.DATA_Y];
                    z = event.values[SensorManager.DATA_Z];

                    float speed = Math.abs(x+y+z - last_x - last_y - last_z) / 100 * 10000;

                    if (speed > 1500) {
                        Log.d("sensor", "shake detected w/ speed: " + speed);

                        comentaryAdapter.Update(userCommented);
                    }
                    last_x = x;
                    last_y = y;
                    last_z = z;

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(mSensorEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(mSensorEventListener, Msensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void update(List<Comentary> userCommented){
        this.userCommented = userCommented;

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int nCurrentOrientation = getScreenOrientation();
        LinearLayout lista = (LinearLayout) findViewById(R.id.listaLinear);
        float per = 0;
        if (nCurrentOrientation==Configuration.ORIENTATION_PORTRAIT){
            per = (float)0.50;
        }
        else{
            per = (float)0.70;
        }
        lista.setWeightSum(per);
    }

    public int getScreenOrientation()
    {
        Display getOrient = getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if(getOrient.getWidth()==getOrient.getHeight()){
            orientation = Configuration.ORIENTATION_SQUARE;
        } else{
            if(getOrient.getWidth() < getOrient.getHeight()){
                orientation = Configuration.ORIENTATION_PORTRAIT;
            }else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }

}
