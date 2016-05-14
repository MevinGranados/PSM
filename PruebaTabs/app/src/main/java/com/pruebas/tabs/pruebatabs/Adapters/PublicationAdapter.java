package com.pruebas.tabs.pruebatabs.Adapters;

import android.gesture.GestureOverlayView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pruebas.tabs.pruebatabs.Main;
import com.pruebas.tabs.pruebatabs.Model.Contacto;
import com.pruebas.tabs.pruebatabs.Model.Publication;
import com.pruebas.tabs.pruebatabs.R;

import java.util.List;

/**
 * Created by Kevin on 02/05/2016.
 */
public class PublicationAdapter extends BaseAdapter{
    List<Contacto> listPublication;

    public PublicationAdapter(List<Contacto> listPublication) {
        this.listPublication = listPublication;
    }

    @Override
    public int getCount() {
        return listPublication.size();
    }

    @Override
    public Contacto getItem(int position) {
        return listPublication.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listPublication.get(position).getIdAmigo1();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.activity_publicacion, null);
        }

        TextView ContactoNombre = (TextView) convertView.findViewById(R.id.ContactoNombre);
        TextView ContactoPuesto = (TextView) convertView.findViewById(R.id.ContactoPuesto);
        ImageView img = (ImageView) convertView.findViewById(R.id.FotoContactoNombre);

        Contacto publication = getItem(position);
        ContactoNombre.setText(publication.getmName());
        ContactoPuesto.setText(publication.getPuesto());
/*        if (publication.getLista() == null && publication.getIdPublication()==i) {
            i++;
            publication.setLista((ListView) convertView.findViewById(R.id.ContactoComentarios));
            if (publication.getLista().getAdapter() == null) {
                publication.getLista().setAdapter(new ComentaryAdapter(publication.getUserCommented()));
            }
        }
  */      //ComentaryAdapter comentaryAdapter = ;
    //    if(true) {
     //       lista.setAdapter();
      //      Log.w("Paso", ": " + position + ": " + i++);
      //  }
      //  Log.w("Persona", ": " + publication.getUserMain().getNameUser());
        //linearLayout.addView(lista);


        //    addbutton .setOnClickListener(new View.OnClickListener() {
        //        @Override
        //        public void onClick(View v) {
        //            Toast.makeText(parent.getContext(), "Tu fruta es: " + fruta.getTitulo(),
        //                    Toast.LENGTH_SHORT).show();
        //        }
        //    });

        return convertView;
    }
}
