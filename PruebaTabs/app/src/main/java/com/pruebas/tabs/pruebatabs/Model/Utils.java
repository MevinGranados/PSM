package com.pruebas.tabs.pruebatabs.Model;

import android.app.Activity;
import android.content.Intent;

import com.pruebas.tabs.pruebatabs.R;

/**
 * Created by Kevin on 08/05/2016.
 */
public class Utils {
    private static int sTheme;

    public final static int THEME_MATERIAL_LIGHT = 0;
    public final static int THEME_YOUR_CUSTOM_THEME = 1;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        switch (sTheme) {
            default:
            case THEME_MATERIAL_LIGHT:
                activity.setTheme(R.style.Theme_Cvshare);
                break;
            case THEME_YOUR_CUSTOM_THEME:
                activity.setTheme(R.style.Theme_Foodstyle);
                break;
        }
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);

    }

    public static void onActivityCreateSetTheme(Activity activity) {

    }
}
