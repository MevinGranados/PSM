package com.pruebas.tabs.pruebatabs.Model;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by Kevin on 11/05/2016.
 */
public class IdiomaConfig {
    public static void setIdioma(Context context, String len){
        String lenguaje =len;
        Locale loc = new Locale(lenguaje);
        Locale.setDefault(loc);

        Configuration confif = new Configuration();
        confif.locale = loc;

        DisplayMetrics metris = context.getResources().getDisplayMetrics();
        context.getResources().updateConfiguration(confif,metris);
    }
}
