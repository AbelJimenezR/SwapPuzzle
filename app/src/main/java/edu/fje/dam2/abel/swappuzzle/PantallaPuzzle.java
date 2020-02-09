package edu.fje.dam2.abel.swappuzzle;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class PantallaPuzzle extends Menu {

    private Bitmap originalBm;
    private String user;
    LinearLayout ll;
    private Chronometer crono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        ll = findViewById(R.id.elLayout);
        crono = (Chronometer) findViewById(R.id.crono);
        crono.start();
        Intent result=getIntent();
        originalBm= result.getParcelableExtra("imatge");
        user = result.getStringExtra("nomUsuari");

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.contenidorGrid, new FragmentPuzzle());
        transaction.add(R.id.contenidorTemps, new FragmentTemps());
        transaction.commit();


    }


    public Bitmap getMyData() {
        return originalBm;
    }
    public String getUser() {
        return user;
    }
    public String getTemps() { return crono.getText().toString(); }





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


}