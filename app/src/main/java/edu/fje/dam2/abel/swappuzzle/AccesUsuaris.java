package edu.fje.dam2.abel.swappuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AccesUsuaris extends Activity {

    private ListView llistaArtistes;
    private Button botoTornar;
    private List<Usuaris> artistes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llistausuaris);
        botoTornar= findViewById(R.id.buttton5);
        llistaArtistes = findViewById(R.id.llistaArtistes);
        artistes = new ArrayList<>();

        botoTornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccesUsuaris.this,MainActivity.class);
                startActivity(intent);


            }
        });



        artistes= UsuarisIntentService.getLlista();

        Collections.sort(artistes, new Comparator<Usuaris>() {
            @Override
            public int compare(Usuaris usuaris, Usuaris t1) {
                return Integer.parseInt(usuaris.getPuntuacio())-Integer.parseInt(t1.getPuntuacio());
            }
        });

        usuarisLlista artistaAdapter = new usuarisLlista(AccesUsuaris.this, artistes);
        llistaArtistes.setAdapter((ListAdapter) artistaAdapter);

    }

}
