package com.pruebas.tabs.pruebatabs.Model;

/**
 * Created by Kevin on 02/05/2016.
 */
import com.google.gson.Gson;

public class User {
    private int mId;
    private String mUsername;
    private String mPassword;
    private String mName;

    public User() {
    }

    public int getMid() {
        return mId;
    }

    public void setMid(int mid) {
        this.mId = mid;
    }

    public User(String username, String password, String name) {
        mUsername = username;
        mPassword = password;
        mName = name;
    }

    public User(int mid, String mUsername, String mPassword, String mName) {
        this.mId = mid;
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mName = mName;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);

    }
    //MIRA ESTA FUNCION
    public User(String user) {

        Gson gson = new Gson();
        User[] userSesion = gson.fromJson(user, User[].class);
        this.mId = userSesion[0].getMid();
        this.mName= userSesion[0].getName();
        this.mUsername=userSesion[0].getUsername();
        this.mPassword=userSesion[0].getPassword();
        //String[] infoUser0 = user.split(":");
        //String info= infoUser0[1]+infoUser0[2]+infoUser0[3]+infoUser0[4];
        //String[] infoUser = info.split(",");
        //String info2 = infoUser[1]+infoUser[2]+infoUser[3]+infoUser[4];
        //this.mName = infoUser[1];
    }
}
