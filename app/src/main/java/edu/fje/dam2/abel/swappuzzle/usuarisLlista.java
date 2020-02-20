package edu.fje.dam2.abel.swappuzzle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Classe que controla la llista on es mostren els artistes
 * @version 5.0 27.01.2020
 */

public class usuarisLlista  extends ArrayAdapter<Usuaris> {
    private Activity context;
    List<Usuaris> artistes;

    public usuarisLlista(Activity context, List<Usuaris> artistes) {
        super(context, R.layout.usuarisllista, artistes);
        this.context = context;
        this.artistes = artistes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.usuarisllista, null, true);

        TextView nom =  listViewItem.findViewById(R.id.nom);
        TextView puntuacio = listViewItem.findViewById(R.id.puntuacio);

        Usuaris artist = artistes.get(position);
        nom.setText(artist.getNom());
        puntuacio.setText(artist.getPuntuacio());

        return listViewItem;
    }
}
