package com.pruebas.tabs.pruebatabs.Model;

/**
 * Created by Kevin on 11/05/2016.
 */
public class amistad {
    private int idAmigo1;
    private int idAmigo2;
    private String solicitud;

    public amistad(int idAmigo1, int idAmigo2, String solicitud) {
        this.idAmigo1 = idAmigo1;
        this.idAmigo2 = idAmigo2;
        this.solicitud = solicitud;
    }

    public int getIdAmigo1() {
        return idAmigo1;
    }

    public void setIdAmigo1(int idAmigo1) {
        this.idAmigo1 = idAmigo1;
    }

    public int getIdAmigo2() {
        return idAmigo2;
    }

    public void setIdAmigo2(int idAmigo2) {
        this.idAmigo2 = idAmigo2;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }
}
