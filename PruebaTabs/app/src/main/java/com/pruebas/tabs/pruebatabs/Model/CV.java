
package com.pruebas.tabs.pruebatabs.Model;

import com.google.gson.Gson;

/**
 * Created by Kevin on 06/05/2016.
 */
public class CV {
    private int id;
    private String puesto;
    private String direccion;
    private String telefono;
    private String objetivo;
    private String experienciaLaboral;
    private String estudios;
    private String conocimientos;

    public CV(int id, String conocimientos, String direccion, String estudios, String experienciaLaboral, String objetivo, String puesto, String telefono) {
        this.conocimientos = conocimientos;
        this.direccion = direccion;
        this.estudios = estudios;
        this.experienciaLaboral = experienciaLaboral;
        this.id = id;
        this.objetivo = objetivo;
        this.puesto = puesto;
        this.telefono = telefono;
    }

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);

    }
    //MIRA ESTA FUNCION
    public CV fromJSON(String user) {
        Gson gson = new Gson();
        CV[] cv = gson.fromJson(user, CV[].class);
        this.conocimientos = cv[0].getConocimientos();
        this.direccion = cv[0].getDireccion();
        this.estudios = cv[0].getEstudios();
        this.experienciaLaboral = cv[0].getExperienciaLaboral();
        this.id = cv[0].getId();
        this.objetivo = cv[0].getObjetivo();
        this.puesto = cv[0].getPuesto();
        this.telefono = cv[0].getTelefono();
        return cv[0];
        //return null;
    }
    public String getConocimientos() {
        return conocimientos;
    }

    public void setConocimientos(String conocimientos) {
        this.conocimientos = conocimientos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }

    public String getExperienciaLaboral() {
        return experienciaLaboral;
    }

    public void setExperienciaLaboral(String experienciaLaboral) {
        this.experienciaLaboral = experienciaLaboral;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
