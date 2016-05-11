package com.pruebas.tabs.pruebatabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.pruebas.tabs.pruebatabs.Model.IdiomaConfig;
import com.pruebas.tabs.pruebatabs.Model.ThemeApplication;
import com.pruebas.tabs.pruebatabs.Model.Utils;
import com.pruebas.tabs.pruebatabs.R;

import java.util.Set;

/**
 * Created by Kevin on 06/05/2016.
 */
public class Settings extends Activity {
    private Spinner spThemes;
    private Spinner spLenguage;
    private CheckBox cbNotif;
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Settings.this, Main.class);
        startActivity(intent);
        Settings.this.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences("_PREFERENCES_", 0);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (settings.getInt("colorLenguage", 0)==0){
            IdiomaConfig.setIdioma(getBaseContext(),"es");
        }
        else{
            IdiomaConfig.setIdioma(getBaseContext(),"en");
        }


        setContentView(R.layout.settings);
        spThemes = (Spinner) findViewById(R.id.spColor);
        spLenguage = (Spinner) findViewById(R.id.spIdioma);
        cbNotif = (CheckBox) findViewById(R.id.NotificacionesCB);

        boolean get = settings.getBoolean("backgroundservice", false);
        cbNotif.setChecked(get);

        String[] items = { "CVShare",  "Food"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Settings.this,
                android.R.layout.simple_spinner_dropdown_item, items);
        spThemes.setAdapter(adapter);

        spThemes.setSelection(settings.getInt("colorTheme", 0));
        ThemeApplication.currentPosition = spThemes.getSelectedItemPosition();

        spThemes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (ThemeApplication.currentPosition != position) {
                    SharedPreferences.Editor editor = getSharedPreferences("_PREFERENCES_", MODE_PRIVATE).edit();
                    editor.putInt("colorTheme", position);
                    editor.commit();
                    ThemeApplication.currentPosition = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] lenguage = { "Español",  "English"};

        adapter = new ArrayAdapter<String>(Settings.this,
                android.R.layout.simple_spinner_dropdown_item, lenguage);
        spLenguage.setAdapter(adapter);

        spLenguage.setSelection(settings.getInt("colorLenguage", 0));
        ThemeApplication.currentLenguage = spLenguage.getSelectedItemPosition();

        spLenguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (ThemeApplication.currentLenguage != position) {
                    SharedPreferences.Editor editor = getSharedPreferences("_PREFERENCES_", MODE_PRIVATE).edit();
                    editor.putInt("colorLenguage", position);
                    editor.commit();
                    ThemeApplication.currentLenguage = position;
                    Intent intent = new Intent(Settings.this, Settings.class);
                    startActivity(intent);
                    Settings.this.finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        cbNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                   SharedPreferences.Editor editor = getSharedPreferences("_PREFERENCES_", MODE_PRIVATE).edit();
                                                   editor.putBoolean("backgroundservice", isChecked);
                                                   editor.commit();
                                                   if (isChecked) {
                                                       startService();
                                                   } else {
                                                       stopService();
                                                   }
                                               }
                                           }
        );
    }

    public void startService(){
        Intent intent = new Intent(this,MyService.class);
        startService(intent);
    }

    public void stopService(){
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
    }
}
