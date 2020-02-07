package edu.fje.dam2.abel.swappuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PERMIS_CAMARA = 200;
    private static final int PERMIS_CAPTURA_IMATGE = 300;
    private GridView imatges;
    private Button boto;
    private ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(9);
    private ArrayList<Bitmap> chunkedImagesOr = new ArrayList<Bitmap>(9);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boto = (Button) findViewById(R.id.botoFoto);
        imatges = (GridView) findViewById(R.id.gridView);

        boto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMIS_CAMARA);
                    return;
                }
                fesFoto(view);
            }
        });

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

                Utilitat.comprovaSiComplet(chunkedImages, chunkedImagesOr);
            }

        });

    }

    private void fesFoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, PERMIS_CAPTURA_IMATGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PERMIS_CAPTURA_IMATGE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap originalBm = (Bitmap) extras.get("data");
                chunkedImages = Utilitat.splitImage(originalBm, 9);
                chunkedImagesOr = (ArrayList<Bitmap>) chunkedImages.clone();
                // Collections.shuffle(chunkedImages);
                ImageAdapter ia = new ImageAdapter(getApplicationContext(), chunkedImages);
                imatges.setAdapter(ia);

            }
        }
    }
}