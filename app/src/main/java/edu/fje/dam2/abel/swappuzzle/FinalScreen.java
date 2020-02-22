package edu.fje.dam2.abel.swappuzzle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FinalScreen extends Menu {
    private TextView textView4;
private int dw;
    private Button boto;
    private Button boto2;
    private String userName;
    private String moviments;
    private ListView llistaUsuaris;

    @SuppressLint("ResourceType")
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
        animacio();
        Intent intent = new Intent(this,UsuarisIntentService.class) ;
        intent.putExtra("operacio","inici");
        intent.putExtra("nom", userName);
        intent.putExtra("puntuacio", moviments);
        startService(intent);

        intent.putExtra("operacio","veure");
        startService(intent);


    }



    public void animacio(){

        ObjectAnimator animacio1 =
                ObjectAnimator.ofFloat(textView4, "y", 0, 1200);
        animacio1.setDuration(1000);
        ObjectAnimator animacio2 =
                ObjectAnimator.ofFloat(textView4, "y", 1200,400);
        animacio1.setDuration(1000);
        ObjectAnimator animacio3 =
                ObjectAnimator.ofFloat(textView4, "y", 400,800);
        animacio1.setDuration(1000);
        ObjectAnimator animacio4 =
                ObjectAnimator.ofFloat(textView4, "y", 800,600);
        animacio1.setDuration(1000);


        final AnimatorSet animacio = new AnimatorSet();
        animacio.playSequentially(animacio1, animacio2, animacio3, animacio4);



        animacio.start();


    }

}