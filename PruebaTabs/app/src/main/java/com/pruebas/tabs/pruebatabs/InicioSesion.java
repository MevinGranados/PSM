package com.pruebas.tabs.pruebatabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pruebas.tabs.pruebatabs.Model.User;
import com.pruebas.tabs.pruebatabs.Net.Net;
import com.pruebas.tabs.pruebatabs.data.UserDataSource;

/**
 * Created by hanyd on 2016-04-30.
 */
public class InicioSesion extends Activity {
    Button inicioSesion;
    Button registro;
    EditText email;
    EditText contrasenia;
    User MyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        SharedPreferences settings = getSharedPreferences("_PREFERENCES_", 0);
        boolean signed = settings.getBoolean("singed", false);
        UserDataSource uds = new UserDataSource(InicioSesion.this);
        if (signed){
            intent = new Intent(InicioSesion.this, Main.class);
            startActivity(intent);
            InicioSesion.this.finish();
            return;
        }
        MyUser = uds.getUser();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_inicio_sesion);
        intent = getIntent();

        inicioSesion = (Button) findViewById(R.id.InicioSesionBtn);
        email = (EditText) findViewById(R.id.eMail);
        contrasenia = (EditText) findViewById(R.id.password);
        registro = (Button) findViewById(R.id.registro);

        String name = intent.getStringExtra("action");
        if (name!=null && name.equals("passEmail")){
            email.setText(intent.getStringExtra("emailName"));
        }
        else if (MyUser!=null){
            email.setText(MyUser.getUsername());
        }


        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myEmail = email.getText().toString();
                String myPass = email.getText().toString();
                if (!myEmail.equals("")){
                    User user = new User();
                    user.setUsername(email.getText().toString());
                    user.setPassword(contrasenia.getText().toString());

                    Net net = new Net(InicioSesion.this);
                    net.execute("login", user);
                }
                else if (myEmail.equals("")){
                    Toast.makeText(InicioSesion.this, "Ingrese correo electronico", Toast.LENGTH_SHORT).show();
                }
                else if (myPass.equals("")){
                    Toast.makeText(InicioSesion.this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(InicioSesion.this, "Ingrese correo electronico", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioSesion.this, Registro.class);
                startActivity(intent);
                InicioSesion.this.finish();
            }
        });
    }

        public void InitSession(User condition){

        if (condition == null) {
            Toast.makeText(InicioSesion.this, "Contraseña y/o Usuario no valido", Toast.LENGTH_SHORT).show();
        } else {
            email.setText("");
            contrasenia.setText("");
            UserDataSource uds = new UserDataSource(InicioSesion.this);

            SharedPreferences.Editor editor = getSharedPreferences("_PREFERENCES_", MODE_PRIVATE).edit();
            editor.putBoolean("singed", true);
            editor.commit();
            if (MyUser==null){

                uds.addNote(condition);
            }
            MyUser = condition;
            uds.updateNote(MyUser);
            Intent intent = new Intent(InicioSesion.this, Main.class);
            startActivity(intent);
            InicioSesion.this.finish();
        }
    }
}

