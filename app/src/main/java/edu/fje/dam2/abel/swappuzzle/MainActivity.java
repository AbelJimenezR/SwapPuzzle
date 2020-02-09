package edu.fje.dam2.abel.swappuzzle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Menu {

    private static final int PERMIS_CAPTURA_IMATGE = 300;
    private static String userName;
    private Button botoInici;
    private Button botoSortir;
    private Intent intent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent= new Intent(this, AudioIntentService.class);
        intent.putExtra("operacio", "inici");
        startService(intent);

        setContentView(R.layout.main_activity);

        botoInici = findViewById(R.id.botoInici);
        botoSortir = findViewById(R.id.botoSortir);


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
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("CAMERA", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                fesFoto();
            }
        });

        builder.show();
    }
}
