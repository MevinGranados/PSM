/*
* Creado por Hermosa Programación - www.hermosaprogramacion.com
*
* Artículo: Usar Tabs en la Action Bar con Android
* Objetivos:
*           - Implementar la navageción con pestañas en Android
*           - Usar un ViewPager para dar efecto deslizante
*
*
* */

package com.pruebas.tabs.pruebatabs;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.provider.Settings.System;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pruebas.tabs.pruebatabs.Model.CV;
import com.pruebas.tabs.pruebatabs.Model.CVSFragment;
import com.pruebas.tabs.pruebatabs.Model.CVSPagerAdapter;
import com.pruebas.tabs.pruebatabs.Model.Contacto;
import com.pruebas.tabs.pruebatabs.Model.IdiomaConfig;
import com.pruebas.tabs.pruebatabs.Model.User;
import com.pruebas.tabs.pruebatabs.Net.Net;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;


public class Main extends FragmentActivity implements ActionBar.TabListener{

    CVSPagerAdapter adapter;


    ViewPager viewpager;
    public static User MyUser;
    public static List<Contacto>contactos;
    public static List<Contacto>contactosTodos;


    public static CV myCV;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            
            case R.id.action_settings:

                intent = new Intent(Main.this, Settings.class);
                startActivity(intent);
                Main.this.finish();
                break;
            case R.id.action_exit:
                SharedPreferences.Editor editor = getSharedPreferences("_PREFERENCES_", MODE_PRIVATE).edit();
                editor.putBoolean("singed", false);
                editor.commit();
                intent = new Intent(Main.this, InicioSesion.class);
                startActivity(intent);
                Main.this.finish();
                break;
            default:
                break;
        }

        return true;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences("_PREFERENCES_", 0);

        int theme = settings.getInt("colorTheme", 0);
        if (theme==0){
            setTheme(R.style.Theme_Cvshare);
        }
        else{
            setTheme(R.style.Theme_Foodstyle);
        }
        if (settings.getInt("colorLenguage", 0)==0){
            IdiomaConfig.setIdioma(getBaseContext(), "es");
        }
        else{
            IdiomaConfig.setIdioma(getBaseContext(), "en");
        }

        setContentView(R.layout.activity_main);



        CargarComentarios();

       // Obtener instancia de la Action Bar
        final ActionBar actionBar = getActionBar();

        // Activar el modo de navegación con tabs en la Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Deshabilitar el caret Up del icono de la aplicación
        actionBar.setHomeButtonEnabled(false);

        // Crear adaptador de fragmentos
        adapter = new CVSPagerAdapter(getSupportFragmentManager());
        adapter.context = Main.this;

        // Obtener el ViewPager y setear el adaptador y la escucha
        viewpager = (ViewPager) findViewById(R.id.pager);
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // Coordinar el item del pager con la pestaña
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // Añadir 3 pestañas y asignarles un título y escucha
        for (int i = 0; i < adapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            //.setIcon(idIcons[i])
                            // Habilita el titulo si lo prefieres
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this));
        }

    }

    void CargarComentarios(){
        contactos = new ArrayList<>();
        Net net = new Net(Main.this);
        net.execute("loadContactos", MyUser.getMid(),"loadAll", MyUser.getMid());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // Nada por hacer


    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // Coordinar la pestaña seleccionada con el item del viewpager


        viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // Nada por hacer
    }
    @Override
    public void onBackPressed() {
        exit(0);
    }

    public void loadContactos(List<Contacto> contactosP){
        this.contactos = contactosP;
    }

    public void loadContactosT(List<Contacto> contactosP){
        this.contactosTodos = contactosP;
    }

    public void loadCV(CV cvP){
        this.myCV = cvP;
    }

}
