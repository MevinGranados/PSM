package com.pruebas.tabs.pruebatabs.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pruebas.tabs.pruebatabs.Model.Contacto;
import com.pruebas.tabs.pruebatabs.Model.Publication;
import com.pruebas.tabs.pruebatabs.Model.User;
import com.pruebas.tabs.pruebatabs.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

/**
 * Created by Kevin on 03/05/2016.
 */
public class ContactsAdapter extends BaseAdapter implements Filterable{
    List<Contacto> listUser;
    public List<Contacto> orig;

    public ContactsAdapter(List<Contacto> listUser) {
        this.listUser = listUser;

    }

    @Override
    public android.widget.Filter getFilter() {
        return new android.widget.Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<Contacto> results = new ArrayList<>();
                if (orig == null)
                    orig = listUser;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Contacto g : orig) {
                            if (g.getmName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                        for (final Contacto g : orig) {
                            if (g.getmUsername().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                listUser = (ArrayList<Contacto>) results.values;
                notifyDataSetChanged();
            }
        };

    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Contacto getItem(int position) {
        return listUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.contact_design, null);
        }
        TextView ContactoNombre = (TextView)convertView.findViewById(R.id.nombreContacto);
        TextView ContactoPuesto = (TextView)convertView.findViewById(R.id.correoElectronico);
        ImageView img = (ImageView)convertView.findViewById(R.id.imagenContacto);


        Contacto publication = getItem(position);
        ContactoNombre.setText(publication.getmName());
        ContactoPuesto.setText(publication.getmUsername());

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
