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

public class ImatgesIntentService extends IntentService {


    private static List<ImatgesFirebase> imatges;
    private DatabaseReference DBArtistes;

    public ImatgesIntentService() {
        super("LlistaImatges");


    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        imatges = new ArrayList<>();
        DBArtistes = FirebaseDatabase.getInstance().getReference().child("Imatges");

        DBArtistes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ImatgesFirebase artista = postSnapshot.getValue(ImatgesFirebase.class);
                    imatges.add(artista);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
          //  puntuacio = intent.getStringExtra("puntuacio");

            String imatge= intent.getStringExtra("sol");
            String operacio = intent.getStringExtra("operacio");
           // nom = intent.getStringExtra("nom");
            switch (operacio) {
                case "inici":
                    afegirImatge(imatge);
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

    }


    public void afegirImatge(String img) {

        if (!TextUtils.isEmpty(img)) {
            String id = DBArtistes.push().getKey();
            ImatgesFirebase imatge = new ImatgesFirebase(img);
            DBArtistes.child(id).setValue(imatge);

        }
    }

    public static List<ImatgesFirebase> getLlista(){
        return imatges;
    }
}
