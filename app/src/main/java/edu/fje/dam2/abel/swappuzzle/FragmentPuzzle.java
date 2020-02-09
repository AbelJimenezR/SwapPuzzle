package edu.fje.dam2.abel.swappuzzle;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentPuzzle extends Fragment {

    private GridView imatges;
    private String usuari;
    private String temps;
    private Button boto;
    private ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(9);
    private ArrayList<Bitmap> chunkedImagesOr = new ArrayList<Bitmap>(9);
    private Bitmap originalBm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_puzzle,
                container, false);

        final PantallaPuzzle activity = (PantallaPuzzle) getActivity();
        originalBm=activity.getMyData();
        usuari=activity.getUser();

        imatges = (GridView) view.findViewById(R.id.gridView);
        chunkedImages = Utilitat.splitImage(originalBm, 9);
        chunkedImagesOr = (ArrayList<Bitmap>) chunkedImages.clone();
        //Collections.shuffle(chunkedImages);
        ImageAdapter ia = new ImageAdapter(getActivity().getApplication().getApplicationContext(), chunkedImages);
        imatges.setAdapter(ia);

        imatges.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Integer posBuida = 0;

                for (int x = 0; x < chunkedImages.size(); x++) {
                    if (chunkedImages.get(x) == null) {
                        posBuida = x;
                    }
                }

                if (ComprovaPosicions.comprovaEsquerra(position, posBuida) || ComprovaPosicions.comprovaDreta(position, posBuida)
                        || ComprovaPosicions.comprovaAmunt(position, posBuida) || ComprovaPosicions.comprovaAvall(position, posBuida)) {

                    chunkedImages.set(posBuida, chunkedImages.get(position));
                    chunkedImages.set(position, chunkedImagesOr.get(8));
                }

                Adapter im = imatges.getAdapter();
                imatges.setAdapter((ListAdapter) im);

                if(Utilitat.comprovaSiComplet(chunkedImages, chunkedImagesOr)){
                    temps=activity.getTemps();
                    enviarMissatge();

                    activity.finish();
                }
            }

        });


        return view;
    }

    public void enviarMissatge(){

        Intent intent = new Intent(getContext(), FinalScreen.class);
        intent.putExtra("temps",temps);
        intent.putExtra("usuari",usuari);
        startActivity(intent);

    }

}
