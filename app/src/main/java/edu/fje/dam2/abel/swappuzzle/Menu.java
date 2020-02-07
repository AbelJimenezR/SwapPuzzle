package edu.fje.dam2.abel.swappuzzle;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;




    public class Menu extends AppCompatActivity {

        @Override
        public boolean onCreateOptionsMenu(android.view.Menu menu) {
            getMenuInflater().inflate(R.menu.m4_barra_accio, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.Ayuda:
                    this.finishAffinity();

                    // Intent intent2 = new Intent(this, web.class);
                    //startActivity(intent2);
                    return true;
                case R.id.exit:
                    this.finishAffinity();
                    System.exit(0);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }

