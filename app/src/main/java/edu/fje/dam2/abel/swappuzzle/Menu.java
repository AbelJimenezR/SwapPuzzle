package edu.fje.dam2.abel.swappuzzle;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class Menu extends AppCompatActivity {
    private boolean isChecked = true;

    private Intent intent;

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.m4_barra_accio, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        intent = new Intent(this, AudioIntentService.class);

        switch (item.getItemId()) {
            case R.id.myswitch:
                if (isChecked) {
                    isChecked = !item.isChecked();
                    item.setChecked(isChecked);
                    intent.putExtra("operacio", "pausa");
                    startService(intent);
                } else {
                    isChecked = !item.isChecked();
                    item.setChecked(isChecked);
                    intent.putExtra("operacio", "inici");
                    startService(intent);
                }
                break;

            case R.id.exit:
                this.finishAffinity();
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
return true;

    }
}


