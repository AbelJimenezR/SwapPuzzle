package edu.fje.dam2.abel.swappuzzle;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Menu {
    private static final int PERMIS_CAMARA = 200;
    private static final int PERMIS_CAPTURA_IMATGE = 300;
    private static String userName;
    private Button botoInici;
    private Button botoSortir;
    private Intent intent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        botoInici = findViewById(R.id.botoInici);
        botoSortir = findViewById(R.id.botoSortir);
        float s= botoSortir.getX();
        Log.i("sd",String.valueOf(s));


        botoInici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askUser();

            }
        });
        botoSortir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);

            }
        });

    }


    // Número que identifica l'activitat de l'aplicació de fotos
    private static final int APP_CAMERA = 0;
    // Identificador de la imatge que crearà l'aplicació de fotos
    private Uri identificadorImatge;

    public void fesFoto() {
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
                Intent intent = new Intent(this, PantallaPuzzle.class);
                intent.putExtra("imatge", originalBm);
                intent.putExtra("nomUsuari", userName);



                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                originalBm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();


                try {
                    desar(byteArray);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startActivity(intent);

            }
        }
    }

    public void askUser() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Introdueix el teu nom");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                userName = input.getText().toString();
                askUser2();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void askUser2() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tria la font");
        builder.setPositiveButton("FIREBASE", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            Intent intent = new Intent(MainActivity.this,AccesImatges.class);
                intent.putExtra("nomUsuari", userName);
            startActivity(intent);

            }
        });

        builder.setNegativeButton("CAMERA", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMIS_CAMARA);
                    return;
                }


                fesFoto();
            }
        });

        builder.show();
    }

    private void desar(byte[] bytes) throws IOException {


        final File fitxer = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/pic.jpg");

        OutputStream output = null;
        try {
            output = new FileOutputStream(fitxer);
            output.write(bytes);
        } finally {
            if (null != output) {
                output.close();
            }
        }
    }
}
