package edu.fje.dam2.abel.swappuzzle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.util.List;

public class PantallaPuzzle extends Menu implements FragmentPuzzle.afegirMoviment {
    private Bitmap originalBm;
    private String user;
    LinearLayout ll;


    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof FragmentPuzzle) {
            FragmentPuzzle detallFragmentActivity = (FragmentPuzzle) fragment;
            detallFragmentActivity.setafegirMoviment(this);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_puzzle_land);
        } else {
            setContentView(R.layout.activity_puzzle);
        }


        ll = findViewById(R.id.elLayout);

        Intent result=getIntent();

        if(result.getParcelableExtra("imatge")!=null) {
            originalBm = result.getParcelableExtra("imatge");
        }else{
           String imatgeId = result.getStringExtra("imatgeId");
            List<ImatgesFirebase> imatges= AccesImatges.imatges;
            for(ImatgesFirebase d : imatges){
                if(d.getNom().equals(imatgeId)){
                    byte[] imageAsBytes = Base64.decode(d.getValor(),Base64.DEFAULT);
                    Bitmap bm= BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    originalBm=bm;

                }
            }
        }


        user = result.getStringExtra("nomUsuari");

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.contenidorGrid, new FragmentPuzzle());
        transaction.add(R.id.contenidorTemps, new FragmentMoviments());
        transaction.commit();


    }


    public Bitmap getMyData() {
        return originalBm;
    }
    public String getUser() {
        return user;
    }





    @Override
    public void onConfigurationChanged(Configuration novaConfiguracio) {
        super.onConfigurationChanged(novaConfiguracio);
        if (novaConfiguracio.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();

            CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,
                    CoordinatorLayout.LayoutParams.MATCH_PARENT);

            ll.setLayoutParams(lp);
            ll.setOrientation(LinearLayout.HORIZONTAL);

        } else if (novaConfiguracio.orientation == Configuration.ORIENTATION_PORTRAIT) {

            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();

          CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,
                    CoordinatorLayout.LayoutParams.MATCH_PARENT);
            ll.setLayoutParams(lp);
            ll.setOrientation(LinearLayout.VERTICAL);
        }
    }



    @Override
    public void afegirMoviment(String nom) {


        FragmentMoviments fragmentTemps = (FragmentMoviments)
                getFragmentManager().findFragmentById(R.id.contenidorTemps);

        fragmentTemps.afegirMoviment(nom);

    }
}