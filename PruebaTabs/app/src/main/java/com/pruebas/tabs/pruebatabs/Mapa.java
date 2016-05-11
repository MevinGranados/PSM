package com.pruebas.tabs.pruebatabs;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kevin on 08/05/2016.
 */
public class Mapa extends Activity implements OnMapReadyCallback {
    GoogleMap map;

    // LocationManager: Nos permite obtener la ubicacion del usuario en tiempo real a traves de diferentes metodos.
    LocationManager locationManager;

    // Extra: Lo utilizamos para agregar marcadores al mapa
    List<LatLng> markerPositions;

    MarkerOptions opts;

    Button buttonLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences("_PREFERENCES_", 0);
        int theme = settings.getInt("colorTheme", 0);
        if (theme == 0) {
            setTheme(R.style.Theme_Cvshare);
        } else {
            setTheme(R.style.Theme_Foodstyle);
        }
        setContentView(R.layout.activity_location);
        buttonLocation = (Button) findViewById(R.id.idLocationB);
        opts = new MarkerOptions();
        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opts.getTitle()!=null) {
                    SharedPreferences.Editor editor = getSharedPreferences("_PREFERENCES_", MODE_PRIVATE).edit();
                    editor.putString("userDirection", opts.getTitle());
                    editor.commit();
                    Mapa.this.finish();
                }
                else{
                   showToast("");
                }
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.MyMapFrag);

        // Como estamos implementando OnMapReadyCallback (Ver arriba) esto quiere decir que se debe de encontrar
        // el metodo "onMapReady" y una ves que se termine de cargar el mapa se llamara a este metodo para poder
        // comenzar a utilizar el objeto mapa
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Instaciammos nuestor objeto mapa
        map = googleMap;

        // Inicializa nuestro objeto LocationManager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Muestra el boton de "Mi Ubicacion" en el mapa (El tipico circulo azul de google)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);

        // Continuamos obteniendo la ubicacion del usuario para despues mostrar esa ubucacion en el mapa por default
        // pero cuando no se encuentre la ubicacion entonces pondremos una ubicacion fija.
        LatLng currentLocation = null;
        try {

            // Utilizamos el metodo que desarrollamos para obtener la ubicacion del usuario
            currentLocation = getCurrentLocation();
        }catch (SecurityException e) {
            e.printStackTrace();
        }

        // Si se pudo obtener la ubicacion
        if (currentLocation != null) {

            // Movemos la camara para que apunte a otra coordenada diferente e la default
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12.f));
        } else { // Si no se pudo obtener la ubicacion

            // Ponemos una ubicacion fija
            currentLocation = new LatLng(25.7342878,-100.311278);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16.f));
        }
        // Listener para detectar los eventos "Click" dentro del mapa
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            // Este evento nos devuelve la cooordenada geografica donde se dio click dentro del mapa
            @Override
            public void onMapClick(LatLng latLng) {
                // Funcion extra que desarrollamos para agregar marcadores al mapa
                myMarker(latLng, true);
            }

        });

    }

    private void myMarker(LatLng position, boolean clean) {
        if (clean) {
            map.clear();
        }


        // De esta manera se pueden agregar marcadores al mapa

        opts.position(position);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(position.latitude, position.longitude, 1);

            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("");
                }
                opts.title(strReturnedAddress.toString());
            }
            else {
                opts.title("");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            opts.title("ERROR");
        }




        // La clase GoogleMap tiene el metodo addMarker
        map.addMarker(opts);


        if (markerPositions == null)
            markerPositions = new ArrayList<>();

        // EXTRA: Tambien se pueden poner lineas dentro del mapa

        if (markerPositions.size() > 0) {
            LatLng latLng = markerPositions.get(markerPositions.size() - 1);
        }
        markerPositions.add(position);




       // String coord = Double.toString(position.latitude) + " , " + Double.toString(position.longitude);
       // textView.setText(coord);
        // Muestra una linea en el mapa

    }

    private void addMarker(String title, LatLng position, boolean clean, boolean polys) {
        if (clean) {
            map.clear();
        }

        // De esta manera se pueden agregar marcadores al mapa
        MarkerOptions opts = new MarkerOptions();
        opts.position(position);
        opts.title(title);

        // La clase GoogleMap tiene el metodo addMarker
        map.addMarker(opts);

        if (!polys)
            return;

        if (markerPositions == null)
            markerPositions = new ArrayList<>();

        // EXTRA: Tambien se pueden poner lineas dentro del mapa
        PolylineOptions line = new PolylineOptions();
        line.width(8);
        line.color(Color.BLUE);

        if (markerPositions.size() > 0) {
            LatLng latLng = markerPositions.get(markerPositions.size() - 1);
            line.add(latLng);
        }
        line.add(position);
        markerPositions.add(position);

        // Muestra una linea en el mapa
        map.addPolyline(line);
    }


    private LatLng getCurrentLocation() throws SecurityException {

        // Nuestro objeto "Criteria" nos servira para buscar un proovedor que cumpla con el criterio dado,
        // en este caso FINE le indica que la ubicacion sea precisa (incluyendo el GPS, Wifi y Redes Moviles)
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        // Nos devuelve el mejor proveedor en este momento de acuerdo
        // al criterio dado. Los proveedores pueden ser: GPS, Wifi y Redes Moviles (3g, 4g)
        String provider = locationManager.getBestProvider(criteria, true);

        // Confirmamos que el proveedor esta activo en el dispositivo (Si el usuario desactiva el proveedor deberemos buscar otro)
        if (provider == null || !locationManager.isProviderEnabled(provider)) {
            showToast("Proveedor: " + provider + " desactivado");
        } else {
            showToast("Proveedor seleccionado: " + provider);

            // Si esta activo el proveedor continuamos obteniendo la ubicacion
            // El metodo "getLastKnownLocation" nos devuelve la ultima ubicacion conocida del usuario de acuerdo al proveedor
            Location currentLocation = locationManager.getLastKnownLocation(provider);

            if (currentLocation != null) {

                // Simplemente convertimos el objeto Location a LatLgn
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20.f));
                return latLng;
            }

        }

        return null;
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
