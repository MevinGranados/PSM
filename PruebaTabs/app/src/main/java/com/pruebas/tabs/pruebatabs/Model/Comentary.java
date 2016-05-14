package com.pruebas.tabs.pruebatabs.Model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 02/05/2016.
 */
public class Comentary {

    private int idPDeQuien;
    private String mName;
    private String contenido;

    public Comentary(int idPDeQuien, String mName, String contenido) {
        this.idPDeQuien = idPDeQuien;
        this.mName = mName;
        this.contenido = contenido;
    }

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);

    }
    //MIRA ESTA FUNCION
    public static List<Comentary> fromJSON(String user) {
        Gson gson = new Gson();
        Comentary[] comentary = gson.fromJson(user, Comentary[].class);
        List<Comentary> send = new ArrayList<>();
        for (Comentary my : comentary) {
            send.add(my);
        }
        if (send.size() == 0)
            return null;
        else {
            return send;
        }
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getIdPDeQuien() {
        return idPDeQuien;
    }

    public void setIdPDeQuien(int idPDeQuien) {
        this.idPDeQuien = idPDeQuien;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
