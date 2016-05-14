package com.pruebas.tabs.pruebatabs;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by Kevin on 12/05/2016.
 */
public class OtroPerfil extends Activity{
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText7;
    EditText editText8;
    EditText editText9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences("_PREFERENCES_", 0);
        int theme = settings.getInt("colorTheme", 0);
        if (theme == 0) {
            setTheme(R.style.Theme_Cvshare);
        } else {
            setTheme(R.style.Theme_Foodstyle);
        }
        setContentView(R.layout.activity_perfil_editable);
        editText1 = (EditText) findViewById(R.id.idEditableNombre);
        editText2 = (EditText) findViewById(R.id.idEditablePuest);
        editText3 = (EditText) findViewById(R.id.idEditableCorreo);
        editText4 = (EditText) findViewById(R.id.idEditableNumero);
        editText5 = (EditText) findViewById(R.id.idEditableDir);
        editText6 = (EditText) findViewById(R.id.idEditableEstudios);
        editText7 = (EditText) findViewById(R.id.idEditableExp);
        editText8 = (EditText) findViewById(R.id.idEditableObjetivo);
        editText9 = (EditText) findViewById(R.id.idEditableCono);

    }
}