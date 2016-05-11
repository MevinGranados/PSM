package com.pruebas.tabs.pruebatabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.pruebas.tabs.pruebatabs.Model.User;
import com.pruebas.tabs.pruebatabs.Net.Net;

/**
 * Created by hanyd on 2016-05-04.
 */
public class Registro extends Activity {
    Button registroR;
    Button loginR;
    EditText nombreR;
    EditText emailR;
    EditText contraseniaR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.registro);

        nombreR=(EditText) findViewById(R.id.nombreUser);
        emailR=(EditText) findViewById(R.id.eMailR);
        contraseniaR=(EditText) findViewById(R.id.passwordR);
        registroR = (Button) findViewById(R.id.RegistroBtn);
        loginR = (Button) findViewById(R.id.loginR);

        registroR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nombreR.getText().toString().isEmpty()){
                    User user = new User();
                    user.setUsername(emailR.getText().toString());
                    user.setPassword(contraseniaR.getText().toString());
                    user.setName(nombreR.getText().toString());

                    // Nuestra clase Net hereda de AsynTask por lo tanto el trabajo se realiza en otro hilo diferente
                    // al hilo principal de la UI. Con el metodo execute comienza a ejecutar el metodo doInBackground

                    Net net = new Net(Registro.this);
                    net.execute("createAccount", user);

                    Intent intent = new Intent(Registro.this, InicioSesion.class);
                    Bundle b = new Bundle();
                    b.putString("action", "passEmail");
                    b.putString("emailName", emailR.getText().toString());
                    intent.putExtras(b);

                    startActivity(intent);
                    Registro.this.finish();
                }
            }
        });

        loginR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registro.this, InicioSesion.class);
                startActivity(intent);
            }
        });
    }
}
