package com.pruebas.tabs.pruebatabs.Net;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.pruebas.tabs.pruebatabs.InicioSesion;
import com.pruebas.tabs.pruebatabs.Main;
import com.pruebas.tabs.pruebatabs.Model.CV;
import com.pruebas.tabs.pruebatabs.Model.Comentary;
import com.pruebas.tabs.pruebatabs.Model.Contacto;
import com.pruebas.tabs.pruebatabs.Model.User;
import com.pruebas.tabs.pruebatabs.Publicacion;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

//no usar el thread de la activity principal, se requiere usar una tarea asíncrona (AsyncTask)
// AsyncTask nos ayuda a realizar una tarea asincrona (en otro hilo)
// Los parametros (AsyncTask<Param1,Param2,Param3>) al hacer el extend son:
// Param1: El tipo de dato que recibe al momento de ejecutar la tarea asincrona (Ver metodo "execute")
// Param2: El tipo de dato que va a regresar el metodo "doInBackground" al metodo "onPostExecute"
// Param3: El tipo de dato que recibe el metodo "onProgressUpdare"
public class Net extends AsyncTask<Object, Integer, String>{
    //path a donde vamos a conectar, sacar la IP de nuestra PC
    //windows ipconfig
    //Casa Hanieli  192.168.1.71
    //210           148.234.88.242
    //Mi casa       192.168.1.69
    //210 Hanieli-Yo-2
    private static final String SERVER_PATH = "http://192.168.1.71/webservice/cvsWebServ.php";
    //http://192.168.1.149/webservice/webservice.php
    //tiempo que esperará una respuesta antes de cancelar la comunicación
    private static final int TIMEOUT = 3000;

    private String action;
    private Context mContext;
    private User IsSigned;
    private CV cv;
    private static List<Contacto> contactos;
    private static List<Contacto> contactosT;
    private static List<Comentary> comentaries;

    public Boolean IsNetwork;
    // ProgressDialog es solo una animacion tipica que indica que se esta cargando una tarea
    //vista de android con el ícono de cargado
    private ProgressDialog mProgressDialog;

    public Net(Context context) {
        mContext = context;
        IsSigned = null;
        IsNetwork=false;
    }

    // Metodo que se ejecuta antes correr nuestra tarea asincrona
    //mostrar íconos de cargado, etc
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Conectando...");
        mProgressDialog.setCancelable(false); //permitir o no que el usuario lo cancele
        mProgressDialog.show();
    }

    // Metodo que se ejecuta cuando termina nuestra tarea asincrona
    //
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //quita el progressdialog despues de hacer la tarea
        mProgressDialog.dismiss();
        if (mContext.getClass().equals(InicioSesion.class)==true) {
            InicioSesion inicioSesion = (InicioSesion) mContext;
            inicioSesion.InitSession(IsSigned);
        }
        if (mContext.getClass().equals(Main.class)==true && action.equals("loadContactos")) {
            Main main = (Main) mContext;
            main.loadContactos(contactos);
            main.loadContactosT(contactosT);
        }
        if (mContext.getClass().equals(Main.class)==true && action.equals("loadAll")) {
            Main main = (Main) mContext;

        }
        if (mContext.getClass().equals(Publicacion.class)==true && action.equals("loadCometarios")) {
            Publicacion main = (Publicacion) mContext;
            main.update(comentaries);
        }
    }



    // Metodo que ejecuta un bloque de codigo en un hilo nuevo, recibe indeterminada cantidad de parametros
    // del tipo especificado al heredar de AsyncTask
    //cosas que se ejecutan en otro hilo
    @Override
    protected String doInBackground(Object... params) {
        String action = (String) params[0];
        this.action=action;
       // Registro reg = (Registro) mContext;
        IsNetwork = isNetworkAvailable();
        if (IsNetwork) {
            if (action.equals("login")) {
                IsSigned = loginUser((User) params[1]);
            } else if (action.equals("createAccount")) {
                createAccount((User) params[1]);
            } else if (action.equals("loadContactos")){
                loadContactos((int) params[1]);
                loadContactosAll((int) params[1]);
                loadCV((int)params[1]);
            }else if (action.equals("loadCometarios")){
                setComentarios((int) params[1]);
            }else if (action.equals("writeComment")){
                sendComentary((int) params[1], (int) params[2], (String) params[3]);
            }
            else if (action.equals("loadAll")){

            }
        }

        return null;
    }


//detectar si hay internet en el dispositivo
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable();
    }

    public void loadCV(int mId){
        int accountStatus = 0;

        String postParams = "&action=loadCV&mId=" + mId;

        // Contiene la url del servidor y ademas metodos para abrir la conexion
        URL url = null;
        // Objeto por el cual se maneja la conexion y peticiones hacia el servidor
        HttpURLConnection conn = null;
        try {
            url = new URL(SERVER_PATH);
            // Con el metodo "openConnection()" se abre la conexion
            conn = (HttpURLConnection) url.openConnection();

            // setDoInput: Activa y especifica que se esperan valores de regreso del servidor (Response)
            conn.setDoInput(true);
            // setDoOutput: Activa y especifica que se enviaran valores al servidor (Request POST, GET)
            conn.setDoOutput(true);
            // setConnectTimeout: El tiempo que va a esperar la respuesta del servidor, si pasa este tiempo
            // y no hay respuesta se termina la conexion.
            conn.setConnectTimeout(TIMEOUT);
            // setRequestProperty: Investigar Mime, Content-Type al hacer una peticion (HTML, PAPW);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // setFixedLengthStreamingMode: Se especifica el tamano del "request" (lo que se enviara al servidor
            conn.setFixedLengthStreamingMode(postParams.getBytes().length);

            // getOutputStream: Nos da un stream de datos para comenzar a escribir en el. Lo que se escriba es lo que
            // se envia al servidor
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            out.write(postParams.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode(); //200 OK
            Log.w("RESPONSE CODE", "" + responseCode);

            // getInputStream: Nos da un stream de datos para leer lo que el servidor responda (Response)
            //para login, comentarios , etc de la app
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String a = inputStreamToString(in);
            if (a.length()>3)
                cv = cv.fromJSON(a);
            else{
                cv = null;
            }
            //user = null;
            Log.w("CVllego", "");
            // Ya que la respuesta viene en un formato "InputStream" la convertimos a String para poder leerla
            // con el metodo inputStramToString (Realizado por nosotros)
            // accountStatus = Integer.parseInt(inputStreamToString(in));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadContactosAll(int mId){
        int accountStatus = 0;
        //enviar variables al servidor
        //nombre variable igual que en arch php
        // String postParams = "&action=createAccount&userJson="+user.toJSON();

        String postParams = "&action=loadAll&MyIdP=" + mId;

        // Parametros que se enviaran al servidor.
        // Es necesario agregar el caracter "&" seguido del nombre de la variable (llave) despues un "=" seguido del valor
        // Es algo basico de Desarrollo Web pero por lo pronto en android veanlo de tal forma que cada variable
        // que se quiera enviar al servidor debe ponerse un &NombreVariable=Valor para despues recuperar esas variables
        // desde el servidor ya sea por medio de POST (PHP: $_POST['nombreVariable']) o GET (PHP: $_GET['nombreVariable'])
        // Dependiendo del header de la peticion (Content-Type) sera o no necesario codificar los valores a algun formato
        // ejemplo UTF-8.


        // Contiene la url del servidor y ademas metodos para abrir la conexion
        URL url = null;
        // Objeto por el cual se maneja la conexion y peticiones hacia el servidor
        HttpURLConnection conn = null;
        try {
            url = new URL(SERVER_PATH);
            // Con el metodo "openConnection()" se abre la conexion
            conn = (HttpURLConnection) url.openConnection();

            // setDoInput: Activa y especifica que se esperan valores de regreso del servidor (Response)
            conn.setDoInput(true);
            // setDoOutput: Activa y especifica que se enviaran valores al servidor (Request POST, GET)
            conn.setDoOutput(true);
            // setConnectTimeout: El tiempo que va a esperar la respuesta del servidor, si pasa este tiempo
            // y no hay respuesta se termina la conexion.
            conn.setConnectTimeout(TIMEOUT);
            // setRequestProperty: Investigar Mime, Content-Type al hacer una peticion (HTML, PAPW);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // setFixedLengthStreamingMode: Se especifica el tamano del "request" (lo que se enviara al servidor
            conn.setFixedLengthStreamingMode(postParams.getBytes().length);

            // getOutputStream: Nos da un stream de datos para comenzar a escribir en el. Lo que se escriba es lo que
            // se envia al servidor
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            out.write(postParams.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode(); //200 OK
            Log.w("RESPONSE CODE", "" + responseCode);

            // getInputStream: Nos da un stream de datos para leer lo que el servidor responda (Response)
            //para login, comentarios , etc de la app
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String a = inputStreamToString(in);
            if (a.length()>3)
                contactosT = Contacto.fromJSON(a);
            else{
                contactosT = null;
            }
                //user = null;
            Log.w("usuariollego", "");
            // Ya que la respuesta viene en un formato "InputStream" la convertimos a String para poder leerla
            // con el metodo inputStramToString (Realizado por nosotros)
            // accountStatus = Integer.parseInt(inputStreamToString(in));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadContactos(int mId){
        int accountStatus = 0;
        //enviar variables al servidor
        //nombre variable igual que en arch php
        // String postParams = "&action=createAccount&userJson="+user.toJSON();

        String postParams = "&action=loadContactos&mId=" + mId;

        // Parametros que se enviaran al servidor.
        // Es necesario agregar el caracter "&" seguido del nombre de la variable (llave) despues un "=" seguido del valor
        // Es algo basico de Desarrollo Web pero por lo pronto en android veanlo de tal forma que cada variable
        // que se quiera enviar al servidor debe ponerse un &NombreVariable=Valor para despues recuperar esas variables
        // desde el servidor ya sea por medio de POST (PHP: $_POST['nombreVariable']) o GET (PHP: $_GET['nombreVariable'])
        // Dependiendo del header de la peticion (Content-Type) sera o no necesario codificar los valores a algun formato
        // ejemplo UTF-8.


        // Contiene la url del servidor y ademas metodos para abrir la conexion
        URL url = null;
        // Objeto por el cual se maneja la conexion y peticiones hacia el servidor
        HttpURLConnection conn = null;
        try {
            url = new URL(SERVER_PATH);
            // Con el metodo "openConnection()" se abre la conexion
            conn = (HttpURLConnection) url.openConnection();

            // setDoInput: Activa y especifica que se esperan valores de regreso del servidor (Response)
            conn.setDoInput(true);
            // setDoOutput: Activa y especifica que se enviaran valores al servidor (Request POST, GET)
            conn.setDoOutput(true);
            // setConnectTimeout: El tiempo que va a esperar la respuesta del servidor, si pasa este tiempo
            // y no hay respuesta se termina la conexion.
            conn.setConnectTimeout(TIMEOUT);
            // setRequestProperty: Investigar Mime, Content-Type al hacer una peticion (HTML, PAPW);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // setFixedLengthStreamingMode: Se especifica el tamano del "request" (lo que se enviara al servidor
            conn.setFixedLengthStreamingMode(postParams.getBytes().length);

            // getOutputStream: Nos da un stream de datos para comenzar a escribir en el. Lo que se escriba es lo que
            // se envia al servidor
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            out.write(postParams.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode(); //200 OK
            Log.w("RESPONSE CODE", "" + responseCode);

            // getInputStream: Nos da un stream de datos para leer lo que el servidor responda (Response)
            //para login, comentarios , etc de la app
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String a = inputStreamToString(in);
            if (a.length()>3)
                contactos = Contacto.fromJSON(a);
            else{
                contactos = null;
            }
            //user = null;
            Log.w("usuariollego", "");
            // Ya que la respuesta viene en un formato "InputStream" la convertimos a String para poder leerla
            // con el metodo inputStramToString (Realizado por nosotros)
            // accountStatus = Integer.parseInt(inputStreamToString(in));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private int createAccount(User user) {
        int accountStatus = 0;
        //enviar variables al servidor
        //nombre variable igual que en arch php
       // String postParams = "&action=createAccount&userJson="+user.toJSON();
        String postParams = "&action=createAccount&userJson="+user.toJSON();

        // Parametros que se enviaran al servidor.
        // Es necesario agregar el caracter "&" seguido del nombre de la variable (llave) despues un "=" seguido del valor
        // Es algo basico de Desarrollo Web pero por lo pronto en android veanlo de tal forma que cada variable
        // que se quiera enviar al servidor debe ponerse un &NombreVariable=Valor para despues recuperar esas variables
        // desde el servidor ya sea por medio de POST (PHP: $_POST['nombreVariable']) o GET (PHP: $_GET['nombreVariable'])
        // Dependiendo del header de la peticion (Content-Type) sera o no necesario codificar los valores a algun formato
        // ejemplo UTF-8.


        // Contiene la url del servidor y ademas metodos para abrir la conexion
        URL url = null;
        // Objeto por el cual se maneja la conexion y peticiones hacia el servidor
        HttpURLConnection conn = null;
        try {
            url = new URL(SERVER_PATH);
            // Con el metodo "openConnection()" se abre la conexion
            conn = (HttpURLConnection) url.openConnection();

            // setDoInput: Activa y especifica que se esperan valores de regreso del servidor (Response)
            conn.setDoInput(true);
            // setDoOutput: Activa y especifica que se enviaran valores al servidor (Request POST, GET)
            conn.setDoOutput(true);
            // setConnectTimeout: El tiempo que va a esperar la respuesta del servidor, si pasa este tiempo
            // y no hay respuesta se termina la conexion.
            conn.setConnectTimeout(TIMEOUT);
            // setRequestProperty: Investigar Mime, Content-Type al hacer una peticion (HTML, PAPW);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // setFixedLengthStreamingMode: Se especifica el tamano del "request" (lo que se enviara al servidor
            conn.setFixedLengthStreamingMode(postParams.getBytes().length);

            // getOutputStream: Nos da un stream de datos para comenzar a escribir en el. Lo que se escriba es lo que
            // se envia al servidor
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            out.write(postParams.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode(); //200 OK
            Log.w("RESPONSE CODE", "" + responseCode);

            // getInputStream: Nos da un stream de datos para leer lo que el servidor responda (Response)
            //para login, comentarios , etc de la app
            InputStream in = new BufferedInputStream(conn.getInputStream());

            // Ya que la respuesta viene en un formato "InputStream" la convertimos a String para poder leerla
            // con el metodo inputStramToString (Realizado por nosotros)
            accountStatus = Integer.parseInt(inputStreamToString(in));

      } catch (IOException e) {
           e.printStackTrace();
        }

        return accountStatus;
    }

    private User loginUser(User user) {
       /* user = null;


        return user;*/


        int accountStatus = 0;
        //enviar variables al servidor
        //nombre variable igual que en arch php
        // String postParams = "&action=createAccount&userJson="+user.toJSON();
        String postParams = "&action=login&userJson="+user.toJSON();

        // Parametros que se enviaran al servidor.
        // Es necesario agregar el caracter "&" seguido del nombre de la variable (llave) despues un "=" seguido del valor
        // Es algo basico de Desarrollo Web pero por lo pronto en android veanlo de tal forma que cada variable
        // que se quiera enviar al servidor debe ponerse un &NombreVariable=Valor para despues recuperar esas variables
        // desde el servidor ya sea por medio de POST (PHP: $_POST['nombreVariable']) o GET (PHP: $_GET['nombreVariable'])
        // Dependiendo del header de la peticion (Content-Type) sera o no necesario codificar los valores a algun formato
        // ejemplo UTF-8.


        // Contiene la url del servidor y ademas metodos para abrir la conexion
        URL url = null;
        // Objeto por el cual se maneja la conexion y peticiones hacia el servidor
        HttpURLConnection conn = null;
        try {
            url = new URL(SERVER_PATH);
            // Con el metodo "openConnection()" se abre la conexion
            conn = (HttpURLConnection) url.openConnection();

            // setDoInput: Activa y especifica que se esperan valores de regreso del servidor (Response)
            conn.setDoInput(true);
            // setDoOutput: Activa y especifica que se enviaran valores al servidor (Request POST, GET)
            conn.setDoOutput(true);
            // setConnectTimeout: El tiempo que va a esperar la respuesta del servidor, si pasa este tiempo
            // y no hay respuesta se termina la conexion.
            conn.setConnectTimeout(TIMEOUT);
            // setRequestProperty: Investigar Mime, Content-Type al hacer una peticion (HTML, PAPW);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // setFixedLengthStreamingMode: Se especifica el tamano del "request" (lo que se enviara al servidor
            conn.setFixedLengthStreamingMode(postParams.getBytes().length);

            // getOutputStream: Nos da un stream de datos para comenzar a escribir en el. Lo que se escriba es lo que
            // se envia al servidor
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            out.write(postParams.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode(); //200 OK
            Log.w("RESPONSE CODE", "" + responseCode);

            // getInputStream: Nos da un stream de datos para leer lo que el servidor responda (Response)
            //para login, comentarios , etc de la app
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String a = inputStreamToString(in);
            if (a.length()>3)
                user = new User (a);
            else
                user = null;
            Log.w("usuariollego", "" + user);
            // Ya que la respuesta viene en un formato "InputStream" la convertimos a String para poder leerla
            // con el metodo inputStramToString (Realizado por nosotros)
           // accountStatus = Integer.parseInt(inputStreamToString(in));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<Comentary> setComentarios(int idUser){
        int accountStatus = 0;

        String postParams = "&action=loadComment&idParaP=" + idUser;

        // Contiene la url del servidor y ademas metodos para abrir la conexion
        URL url = null;
        // Objeto por el cual se maneja la conexion y peticiones hacia el servidor
        HttpURLConnection conn = null;
        try {
            url = new URL(SERVER_PATH);
            // Con el metodo "openConnection()" se abre la conexion
            conn = (HttpURLConnection) url.openConnection();

            // setDoInput: Activa y especifica que se esperan valores de regreso del servidor (Response)
            conn.setDoInput(true);
            // setDoOutput: Activa y especifica que se enviaran valores al servidor (Request POST, GET)
            conn.setDoOutput(true);
            // setConnectTimeout: El tiempo que va a esperar la respuesta del servidor, si pasa este tiempo
            // y no hay respuesta se termina la conexion.
            conn.setConnectTimeout(TIMEOUT);
            // setRequestProperty: Investigar Mime, Content-Type al hacer una peticion (HTML, PAPW);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // setFixedLengthStreamingMode: Se especifica el tamano del "request" (lo que se enviara al servidor
            conn.setFixedLengthStreamingMode(postParams.getBytes().length);

            // getOutputStream: Nos da un stream de datos para comenzar a escribir en el. Lo que se escriba es lo que
            // se envia al servidor
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            out.write(postParams.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode(); //200 OK
            Log.w("RESPONSE CODE", "" + responseCode);

            // getInputStream: Nos da un stream de datos para leer lo que el servidor responda (Response)
            //para login, comentarios , etc de la app
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String a = inputStreamToString(in);
            if (a.length()>3)
                comentaries = Comentary.fromJSON(a);
            else{
                comentaries = null;
            }
            //user = null;
            Log.w("usuariollego", "");
            // Ya que la respuesta viene en un formato "InputStream" la convertimos a String para poder leerla
            // con el metodo inputStramToString (Realizado por nosotros)
            // accountStatus = Integer.parseInt(inputStreamToString(in));

        } catch (IOException e) {
            e.printStackTrace();
        }


        return comentaries;
    }

    public void sendComentary(int idUser,int idUserPara, String msn){
        int accountStatus = 0;

        String postParams = "&action=writeComment&idDeP=" + idUser + "&idParaP=" + idUserPara + "&contenidoP=" + msn;

        // Contiene la url del servidor y ademas metodos para abrir la conexion
        URL url = null;
        // Objeto por el cual se maneja la conexion y peticiones hacia el servidor
        HttpURLConnection conn = null;
        try {
            url = new URL(SERVER_PATH);
            // Con el metodo "openConnection()" se abre la conexion
            conn = (HttpURLConnection) url.openConnection();

            // setDoInput: Activa y especifica que se esperan valores de regreso del servidor (Response)
            conn.setDoInput(true);
            // setDoOutput: Activa y especifica que se enviaran valores al servidor (Request POST, GET)
            conn.setDoOutput(true);
            // setConnectTimeout: El tiempo que va a esperar la respuesta del servidor, si pasa este tiempo
            // y no hay respuesta se termina la conexion.
            conn.setConnectTimeout(TIMEOUT);
            // setRequestProperty: Investigar Mime, Content-Type al hacer una peticion (HTML, PAPW);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // setFixedLengthStreamingMode: Se especifica el tamano del "request" (lo que se enviara al servidor
            conn.setFixedLengthStreamingMode(postParams.getBytes().length);

            // getOutputStream: Nos da un stream de datos para comenzar a escribir en el. Lo que se escriba es lo que
            // se envia al servidor
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            out.write(postParams.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode(); //200 OK
            Log.w("RESPONSE CODE", "" + responseCode);

            // getInputStream: Nos da un stream de datos para leer lo que el servidor responda (Response)
            //para login, comentarios , etc de la app


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Metodo que lee un String desde un InputStream (Convertimos el InputStream del servidor en un String)
    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder response = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            while((rLine = rd.readLine()) != null)
            {
                response.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

}