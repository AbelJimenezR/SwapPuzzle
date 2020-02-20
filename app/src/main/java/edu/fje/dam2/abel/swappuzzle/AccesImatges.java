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


public class AccesImatges extends Activity {

    private ListView llistaArtistes;
    private Button botoTornar;
    private List<ImatgesFirebase> artistes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llistaimatges);
        botoTornar= findViewById(R.id.buttton5);
        llistaArtistes = findViewById(R.id.llistaImatges);
        artistes = new ArrayList<>();

        botoTornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccesImatges.this,MainActivity.class);
                startActivity(intent);


            }
        });



        artistes= ImatgesIntentService.getLlista();




        imatgesLlista artistaAdapter = new imatgesLlista(AccesImatges.this, artistes);
        llistaArtistes.setAdapter((ListAdapter) artistaAdapter);

    }

}
