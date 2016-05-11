package com.pruebas.tabs.pruebatabs.Model;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pruebas.tabs.pruebatabs.Adapters.ComentaryAdapter;
import com.pruebas.tabs.pruebatabs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 02/05/2016.
 */
public class Publication {
    private User userMain;
    private List<Comentary>userCommented;
    private int idPublication;

    public Publication(User userMain, int idPublication) {
        this.userMain = userMain;
        this.idPublication = idPublication;

    }

    public int getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(int idPublication) {
        this.idPublication = idPublication;
    }

    public User getUserMain() {
        return userMain;
    }

    public void setUserMain(User userMain) {
        this.userMain = userMain;
    }

    public void setComments(View convertView){
        //ComentaryAdapter comentaryAdapter = new ComentaryAdapter(userCommented);
        //rootView.setAdapter(comentaryAdapter);


    }


    public List<Comentary> getUserCommented() {
        return userCommented;
    }

    public void setUserCommented(List<Comentary> userCommented) {
        this.userCommented = userCommented;
    }
}
