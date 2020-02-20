package edu.fje.dam2.abel.swappuzzle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {
    private boolean isChecked ;
    private static boolean isChecked2;
    private AudioManager am;

    MenuItem bubu1;
    MenuItem bubu2;

    private Intent intent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this, AudioIntentService.class);


        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        int requestResult = am.requestAudioFocus(
                focusChangeListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);

        if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED && isChecked==true) {

            intent.putExtra("operacio", "inici");
            startService(intent);

        } else if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            intent.putExtra("operacio", "pause");
            startService(intent);
        } else {
        }
    }


    @Override
    protected void onResume(){
        super.onResume();

        if(isChecked) {
            intent.putExtra("operacio", "inici");
            startService(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isChecked) {

            intent.putExtra("operacio", "pausa");
            startService(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        getMenuInflater().inflate(R.menu.m4_barra_accio, menu);
        bubu1= menu.findItem(R.id.myswitch);
        bubu1.setChecked(getFromSp("cec1"));
        bubu2= menu.findItem(R.id.myswitch2);
        bubu2.setChecked(getFromSp("cec2"));

        if(bubu1.isChecked()) {
        intent.putExtra("operacio", "inici");
        startService(intent);

       }

        return true;
    }

    private boolean getFromSp(String key) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", android.content.Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    private void saveInSp(String key,boolean value){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        intent = new Intent(this, AudioIntentService.class);

        switch (item.getItemId()) {
            case R.id.myswitch:


                if (bubu1.isChecked()) {
                    isChecked=false;
                    bubu1.setChecked(false);
                    saveInSp("cec1",false);
                    intent.putExtra("operacio", "pausa");
                    startService(intent);
                }else{
                    isChecked=true;
                    bubu1.setChecked(true);
                    saveInSp("cec1",true);
                    intent.putExtra("operacio", "inici");
                    startService(intent);


                }
                return true;

            case R.id.myswitch2:

                if (bubu2.isChecked()) {
                    isChecked2=false;
                    bubu2.setChecked(false);
                    saveInSp("cec2",false);

                }else{
                    isChecked2=true;
                    bubu2.setChecked(true);
                    saveInSp("cec2",true);

                }
                return true;


            case R.id.exit:
                this.finishAffinity();
                System.exit(0);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public static boolean getChecked(){
        return isChecked2;
    }

    private AudioManager.OnAudioFocusChangeListener focusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    switch (focusChange) {

                        /*case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) :
                            // Lower the volume while ducking.
                            mp.setVolume(0.2f, 0.2f);
                            break;*/
                        case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) :
                            intent.putExtra("operacio", "pausa");
                            startService(intent);
                            break;

                        case (AudioManager.AUDIOFOCUS_LOSS) :
                            intent.putExtra("operacio", "pausa");
                            startService(intent);
                            break;

                        case (AudioManager.AUDIOFOCUS_GAIN) :
                            intent.putExtra("operacio", "inici");
                            startService(intent);
                            break;

                        default: break;
                    }
                }
            };
}


