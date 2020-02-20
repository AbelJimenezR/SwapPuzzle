package edu.fje.dam2.abel.swappuzzle;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class FragmentPuzzle extends Fragment {
    private MediaPlayer mpSons;
    private int moviments=0;
    private GridView imatges;
    private String usuari;

    private ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(9);
    private ArrayList<Bitmap> chunkedImagesOr = new ArrayList<Bitmap>(9);
    private Bitmap originalBm;
    private  LlistaTask task;
    afegirMoviment callback;


    public void setafegirMoviment(afegirMoviment callback) {
        this.callback = callback;
    }

    // This interface can be implemented by the Activity, parent Fragment,
    // or a separate test implementation.
    interface afegirMoviment {
        void afegirMoviment(String nom);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_puzzle,
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


                    if(Menu.getChecked()) {
                        mpSons = MediaPlayer.create(activity, R.raw.move);
                        mpSons.start();
                    }

                    callback.afegirMoviment(String.valueOf(moviments+1));

                    task= new LlistaTask();
                    task.execute();


                    chunkedImages.set(posBuida, chunkedImages.get(position));
                    chunkedImages.set(position, chunkedImagesOr.get(8));


                }else{
                    if(Menu.getChecked()) {
                        mpSons = MediaPlayer.create(activity, R.raw.error);
                        mpSons.start();
                    }
                }

                Adapter im = imatges.getAdapter();
                imatges.setAdapter((ListAdapter) im);

                if(Utilitat.comprovaSiComplet(chunkedImages, chunkedImagesOr)){
                    enviarMissatge();

                    activity.finish();
                }
            }

        });


        return view;
    }

   public void enviarMissatge(){

        Intent intent = new Intent(getContext(), FinalScreen.class);
        intent.putExtra("moviments",String.valueOf(moviments));
        intent.putExtra("usuari",usuari);
        startActivity(intent);

    }


    public class LlistaTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {
            return String.valueOf(moviments++);
        }

        @Override
        protected void onPostExecute(final String resultat) {

        }
    }

}

