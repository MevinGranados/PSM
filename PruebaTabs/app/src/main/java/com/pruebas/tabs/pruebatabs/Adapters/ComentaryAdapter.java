package com.pruebas.tabs.pruebatabs.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pruebas.tabs.pruebatabs.Model.Comentary;
import com.pruebas.tabs.pruebatabs.Model.Publication;
import com.pruebas.tabs.pruebatabs.R;

import java.util.List;

/**
 * Created by Kevin on 02/05/2016.
 */
public class ComentaryAdapter extends BaseAdapter{
    List<Comentary> comentaryList;

    public ComentaryAdapter(List<Comentary> comentaryList) {
        this.comentaryList = comentaryList;
    }

    @Override
    public int getCount() {
        return comentaryList.size();
    }

    @Override
    public Comentary getItem(int position) {
        return comentaryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Comentary comentary = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (comentary.getUserCommentary()!=null) {

            View convertView2 = inflater.inflate(R.layout.activity_comentario, null);

            if (convertView != convertView2) {
                convertView = convertView2;
                TextView ComentaNombre = (TextView) convertView.findViewById(R.id.ComentaNombre);
                TextView ComentaComentario = (TextView) convertView.findViewById(R.id.ComentaComentario);
                ImageView img = (ImageView) convertView.findViewById(R.id.fotoComenta);

                ComentaNombre.setText(comentary.getUserCommentary().getName());
                ComentaComentario.setText(comentary.getComment());
            }

        }
      /*  else{
            View convertView2 = inflater.inflate(R.layout.activity_anadir_comentario, null);

            if (convertView != convertView2) {
                convertView = convertView2;
                TextView ComentaNombre = (TextView) convertView.findViewById(R.id.UserNombre);
                EditText ComentaComentario = (EditText) convertView.findViewById(R.id.AnadirComentarioEdit);
                ImageView img = (ImageView) convertView.findViewById(R.id.AnadirComentarioFoto);

                ComentaNombre.setText("YOSOYYO");
            }

        }
*/
        return convertView;
    }
}
