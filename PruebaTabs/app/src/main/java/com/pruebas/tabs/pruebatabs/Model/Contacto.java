package com.pruebas.tabs.pruebatabs.Model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 11/05/2016.
 */
public class Contacto {
    private int idAmigo1;
    private String mName;
    private String mUsername;
    private String puesto;

    public Contacto(int idAmigo1, String mName, String mUsername, String puesto) {
        this.idAmigo1 = idAmigo1;
        this.mName = mName;
        this.mUsername = mUsername;
        this.puesto = puesto;
    }

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);

    }
    //MIRA ESTA FUNCION
    public static List<Contacto> fromJSON(String user) {
        Gson gson = new Gson();
        Contacto [] comentary = gson.fromJson(user, Contacto[].class);
        List<Contacto> send = new ArrayList<>();
        for (Contacto my :comentary) {
            send.add(my);
        }

        if (comentary.length>0)
            return send;
        return null;
    }

    public int getIdAmigo1() {
        return idAmigo1;
    }

    public void setIdAmigo1(int idAmigo1) {
        this.idAmigo1 = idAmigo1;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
}
