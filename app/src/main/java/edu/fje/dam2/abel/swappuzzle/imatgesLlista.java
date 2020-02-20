package edu.fje.dam2.abel.swappuzzle;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Classe que controla la llista on es mostren els artistes
 * @version 5.0 27.01.2020
 */

public class imatgesLlista  extends ArrayAdapter<ImatgesFirebase> {
    private Activity context;
    List<ImatgesFirebase> artistes;

    public imatgesLlista(Activity context, List<ImatgesFirebase> artistes) {
        super(context, R.layout.imatgesllista, artistes);
        this.context = context;
        this.artistes = artistes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.imatgesllista, null, true);

        ImageView valor =  listViewItem.findViewById(R.id.valor);



        ImatgesFirebase artist = artistes.get(position);


        String x= artist.getValor();

        byte[] imageAsBytes = Base64.decode(x,Base64.DEFAULT);

        Bitmap bm=BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        bm = Bitmap.createScaledBitmap(bm, 200, 200, true);

        valor.setImageBitmap(bm);


        return listViewItem;
    }
}
