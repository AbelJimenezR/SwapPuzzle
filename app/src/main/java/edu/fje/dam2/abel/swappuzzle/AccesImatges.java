package edu.fje.dam2.abel.swappuzzle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AccesImatges extends Activity {

    private ListView llistaArtistes;
    private Button botoTornar;
    public static List<ImatgesFirebase> imatges;
    private DatabaseReference DBArtistes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llistaimatges);
        FirebaseApp.initializeApp(this);
        DBArtistes = FirebaseDatabase.getInstance().getReference().child("Imatges");

        llistaArtistes = findViewById(R.id.llistaImatges);
        imatges = new ArrayList<>();
       // afegirArtista();

        llistaArtistes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                ImatgesFirebase iF = (ImatgesFirebase) llistaArtistes.getItemAtPosition(position);
                String g=iF.getNom();
                Intent intent2 = getIntent();
                String userName = intent2.getStringExtra("nomUsuari");
                Intent intent = new Intent(AccesImatges.this, PantallaPuzzle.class);
                intent.putExtra("imatgeId", g);
                intent.putExtra("nomUsuari", userName);

                startActivity(intent);

            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        DBArtistes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ImatgesFirebase artista = postSnapshot.getValue(ImatgesFirebase.class);
                    imatges.add(artista);
                }

                imatgesLlista artistaAdapter = new imatgesLlista(AccesImatges.this, imatges);
                llistaArtistes.setAdapter((ListAdapter) artistaAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void afegirArtista() {

        ImageView imageView=findViewById(R.id.iv);
        imageView.setImageResource(R.drawable.elefant);


        Bitmap bm= ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String s = android.util.Base64.encodeToString(byteArray, Base64.DEFAULT);



            String id = DBArtistes.push().getKey();
            ImatgesFirebase artista = new ImatgesFirebase("tigre", s);
            DBArtistes.child(id).setValue(artista);


    }

}
