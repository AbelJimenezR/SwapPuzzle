package edu.fje.dam2.abel.swappuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FinalScreen extends Menu {
    private TextView textView4;

    private Button boto;
    private Button boto2;
    private String userName;
    private  String moviments;
    private ListView llistaUsuaris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_final_screen);

        boto=findViewById(R.id.button3);
        boto2=findViewById(R.id.button4);
        llistaUsuaris=findViewById(R.id.llistaArtistes);
        boto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinalScreen.this,MainActivity.class);
                startActivity(intent);

            }
        });

        boto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinalScreen.this,AccesUsuaris.class);
                startActivity(intent);

            }
        });

        Intent getData=getIntent();
        moviments= getData.getStringExtra("moviments");
        userName= getData.getStringExtra("usuari").toUpperCase();


        textView4= findViewById(R.id.textView4);
        textView4.setText("ENHORABONA " + userName + " HAS EMPRAT " + moviments + " MOVIMENTS");

        Intent intent = new Intent(this,UsuarisIntentService.class) ;
        intent.putExtra("operacio","inici");
        intent.putExtra("nom", userName);
        intent.putExtra("puntuacio", moviments);
        startService(intent);

        intent.putExtra("operacio","veure");
        startService(intent);


    }

}