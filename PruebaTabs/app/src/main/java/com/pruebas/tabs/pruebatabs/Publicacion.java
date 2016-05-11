package com.pruebas.tabs.pruebatabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pruebas.tabs.pruebatabs.Adapters.ComentaryAdapter;
import com.pruebas.tabs.pruebatabs.Model.Comentary;
import com.pruebas.tabs.pruebatabs.Model.Publication;
import com.pruebas.tabs.pruebatabs.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 04/05/2016.
 */
public class Publicacion extends Activity{

    private String UserPublited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        TextView ContactoNombre = (TextView) findViewById(R.id.ContactoNombre);
        TextView ContactoPuesto = (TextView) findViewById(R.id.ContactoPuesto);
        ImageView img = (ImageView) findViewById(R.id.FotoContactoNombre);
        ListView listView = (ListView)findViewById(R.id.ContactoComentarios);

        Intent intent = getIntent();
        UserPublited = intent.getStringExtra("USER");
        if (UserPublited!=null)
            ContactoNombre.setText(UserPublited);
        User userTest = new User("Kevin","Tryhard", "KevinG");
        List<Comentary> userCommented;
        userCommented= new ArrayList<>();
        userCommented.add(new Comentary(userTest,"Hola1"));
        userCommented.add(new Comentary(userTest,"Hola2"));
        userCommented.add(new Comentary(userTest,"Hola3"));
        userCommented.add(new Comentary(userTest,"Hola4"));
        userCommented.add(new Comentary(userTest,"Hola5"));
        userCommented.add(new Comentary(userTest,"Hola6"));
        userCommented.add(new Comentary(userTest,"Hola7"));

        ComentaryAdapter comentaryAdapter = new ComentaryAdapter(userCommented);
        listView.setAdapter(comentaryAdapter);
        comentaryAdapter.notifyDataSetChanged();


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
