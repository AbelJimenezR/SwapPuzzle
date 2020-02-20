package edu.fje.dam2.abel.swappuzzle;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


public class UsuarisIntentService extends IntentService {

    private String puntuacio;
    private String nom;
    private String operacio;
    private static List<Usuaris> artistes;
    private DatabaseReference DBArtistes;

    public UsuarisIntentService() {
        super("LlistaUsuari");


    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        artistes = new ArrayList<>();
        DBArtistes = FirebaseDatabase.getInstance().getReference().child("Usuaris");
    }

    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);

    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (intent != null) {
            puntuacio = intent.getStringExtra("puntuacio");

            operacio = intent.getStringExtra("operacio");
            nom = intent.getStringExtra("nom");
            switch (operacio) {
                case "inici":
                    afegirArtista(nom,puntuacio);
                    break;
                case "veure":
                    veureLlista();
                    break;

                default:
                    break;
            }
        }
        return START_NOT_STICKY;
    }



public void veureLlista(){
    DBArtistes.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            artistes.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Usuaris artista = postSnapshot.getValue(Usuaris.class);
                artistes.add(artista);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}


    public void afegirArtista(String nom, String puntuacio) {

        if (!TextUtils.isEmpty(nom)) {
            String id = DBArtistes.push().getKey();
            Usuaris artista = new Usuaris(nom, puntuacio);
            DBArtistes.child(id).setValue(artista);

        }
    }

    public static List<Usuaris> getLlista(){
        return artistes;
    }
}
