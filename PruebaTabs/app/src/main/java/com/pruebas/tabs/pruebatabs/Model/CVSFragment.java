package com.pruebas.tabs.pruebatabs.Model;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.pruebas.tabs.pruebatabs.Adapters.ContactsAdapter;
import com.pruebas.tabs.pruebatabs.Adapters.PublicationAdapter;
import com.pruebas.tabs.pruebatabs.EditarPerfil;
import com.pruebas.tabs.pruebatabs.InicioSesion;
import com.pruebas.tabs.pruebatabs.Main;
import com.pruebas.tabs.pruebatabs.Mapa;
import com.pruebas.tabs.pruebatabs.NFCService;
import com.pruebas.tabs.pruebatabs.Publicacion;
import com.pruebas.tabs.pruebatabs.R;
import com.pruebas.tabs.pruebatabs.Settings;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hanyd on 2016-04-28.
 */
public class CVSFragment extends Fragment {

    public static final String ARG_SECTION_NAME = "section_name";
    public static final String ARG_SECTION_IMAGE = "section_image";
    public static final String ARG_NUMBER_PAGE = "section_numberPage";

    public CVSFragment() {
        super();
    }

    GestureOverlayView gestureOverlayView;
    GestureDetector mDetector;
    GestureLibrary mGestureLibrary;
    ListView listView;
    PublicationAdapter publicationAdapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        int PAGE = args.getInt(ARG_NUMBER_PAGE);
        View rootView = null;
        listView = null;

        switch (PAGE) {
            case 0: {
                rootView = inflater.inflate(R.layout.activity_newsfeet, container, false);

                // Setear la imagen al fragmento
                listView = (ListView) rootView.findViewById(R.id.idListaNewsFeet);
                final Context myContext = inflater.getContext();

                publicationAdapter = new PublicationAdapter(Main.contactos);

                listView.setAdapter(publicationAdapter);
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View v,
                                                   int pos, long id) {
                        TextView ContactoNombre = (TextView) v.findViewById(R.id.ContactoNombre);
                        Intent intent = new Intent(myContext, Publicacion.class);
                        Bundle b = new Bundle();
                        b.putInt("USER",
                                Main.contactos.get(adapterView.getPositionForView(v)).getIdAmigo1());
                        b.putString("USERname",
                                Main.contactos.get(adapterView.getPositionForView(v)).getmName());
                        b.putString("USERpuesto",
                                Main.contactos.get(adapterView.getPositionForView(v)).getPuesto());
                        intent.putExtras(b);
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                });
                publicationAdapter.notifyDataSetChanged();
                listView.setLongClickable(true);

                gestureOverlayView = (GestureOverlayView) rootView.findViewById(R.id.idGestureNewsFeed);
                gestureOverlayView.getLayoutParams().width = 200;
                mDetector = new GestureDetector(myContext, new GestureDetector.SimpleOnGestureListener());
                mGestureLibrary = GestureLibraries.fromRawResource(myContext, R.raw.gestures);

                if (!mGestureLibrary.load()) {
                    Toast.makeText(myContext, "No se puede usar las gesturas", Toast.LENGTH_SHORT).show();
                }
                gestureOverlayView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
                    @Override
                    public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
                        gestureOverlayView.getLayoutParams().width = 500;
                    }

                    @Override
                    public void onGesture(GestureOverlayView overlay, MotionEvent event) {

                    }

                    @Override
                    public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
                        gestureOverlayView.getLayoutParams().width = 200;
                    }

                    @Override
                    public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

                    }
                });
                gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
                                                                     @Override
                                                                     public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                                                                         List<Prediction> predictions = mGestureLibrary.recognize(gesture);
                                                                         if (predictions.size() > 0) {
                                                                             Prediction prediction = predictions.get(0);
                                                                             if (prediction.score > 1) {
                                                                                 String name = prediction.name;
                                                                                 if (name.equals("Gesture_Cerrar_App")) {
                                                                                     System.exit(0);
                                                                                     //Toast.makeText(myContext, "Gesture_Cerrar_App", Toast.LENGTH_SHORT).show();
                                                                                 } else if (name.equals("Gesture_Config_App")) {
                                                                                     Intent intent = new Intent(myContext, Settings.class);
                                                                                     startActivity(intent);
                                                                                     //myContext;
                                                                                     //Toast.makeText(myContext, "Gesture_Config_App", Toast.LENGTH_SHORT).show();
                                                                                 } /*else if (name.equals("Gesture_Contactos_App")) {
                                                                                     Toast.makeText(myContext, "Gesture_Contactos_App", Toast.LENGTH_SHORT).show();
                                                                                 }*/
                                                                             }
                                                                             Toast.makeText(myContext, "" + prediction.score, Toast.LENGTH_SHORT).show();
                                                                         }
                                                                     }
                                                                 }
                );

            }
            break;
            case 1: {
                final Context myContext = inflater.getContext();
                CV cv = new CV(0, "Tryhardear", "Mi casa", "LMAD FULL", "Que es eso? :3", "Ser feliz", "Ser de rancho", "Mi tel");
                rootView = inflater.inflate(R.layout.activity_perfil, container, false);
                TextView tvText = (TextView) rootView.findViewById(R.id.idNombre);
                tvText.setText("Mi nombre");
                tvText = (TextView) rootView.findViewById(R.id.idPuesto);
                tvText.setText(cv.getPuesto());
                tvText = (TextView) rootView.findViewById(R.id.idDir);
                tvText.setText(cv.getDireccion());
                tvText = (TextView) rootView.findViewById(R.id.idNumeroContacto);
                tvText.setText(cv.getTelefono());
                tvText = (TextView) rootView.findViewById(R.id.idCorrep);
                tvText.setText("Le keven");
                tvText = (TextView) rootView.findViewById(R.id.idObjetivo);
                tvText.setText(cv.getObjetivo());
                tvText = (TextView) rootView.findViewById(R.id.idExperiencia);
                tvText.setText(cv.getExperienciaLaboral());
                tvText = (TextView) rootView.findViewById(R.id.idEstudios);
                tvText.setText(cv.getEstudios());
                tvText = (TextView) rootView.findViewById(R.id.idConocimientos);
                tvText.setText(cv.getConocimientos());
                Button btn = (Button) rootView.findViewById(R.id.idPerfilBtn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(myContext, EditarPerfil.class);
                        startActivity(intent);
                    }
                });

            }
                break;
            case 2: {
                final Context myContext = inflater.getContext();

                rootView = inflater.inflate(R.layout.activity_contacts, container, false);

                SearchView mSearchView;

                final List<User> user = new ArrayList<>();
                ContactsAdapter contactsAdapter;
                mSearchView = (SearchView)rootView.findViewById(R.id.searchView);
                listView = (ListView) rootView.findViewById(R.id.ContactosLV);


                user.add(new User(0, "Hanieli", "Princesa", "Princesa@gmail.com"));
                user.add(new User(0, "Hanieli", "Ella", "Ella@gmail.com"));
                user.add(new User(0, "Hanieli", "GG", "GG@gmail.com"));
                user.add(new User(0, "Hanieli", "RIP", "RIP@gmail.com"));
                user.add(new User(0, "Hanieli", "Hole", "Hole@gmail.com"));

                contactsAdapter = new ContactsAdapter(Main.contactosTodos);
                listView.setAdapter(contactsAdapter);

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View v,
                                                   int pos, long id) {
                        TextView ContactoNombre = (TextView) v.findViewById(R.id.ContactoNombre);
                        Intent intent = new Intent(myContext, Publicacion.class);
                        Bundle b = new Bundle();
                        b.putInt("USER",
                                Main.contactos.get(adapterView.getPositionForView(v)).getIdAmigo1());
                        b.putString("USERname",
                                Main.contactos.get(adapterView.getPositionForView(v)).getmName());
                        b.putString("USERpuesto",
                                Main.contactos.get(adapterView.getPositionForView(v)).getPuesto());
                        intent.putExtras(b);
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                });


                listView.setTextFilterEnabled(true);
                mSearchView.setIconifiedByDefault(false);
                mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (TextUtils.isEmpty(newText)) {
                            listView.clearTextFilter();
                        } else {
                            listView.setFilterText(newText);
                        }
                        return true;
                    }
                });
                mSearchView.setSubmitButtonEnabled(true);
                mSearchView.setQueryHint(getString(R.string.prompt_busqueda));


            }
            break;

        }

        return rootView;
    }


}