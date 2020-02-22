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
        isChecked=getFromSp("cec3");



        am = (AudioManager) getSystemService(AUDIO_SERVICE);

        am.requestAudioFocus(
                focusChangeListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);


       /* if(isChecked&&AudioManager.AUDIOFOCUS_GAIN==0){
            intent.putExtra("operacio", "inici");
            startService(intent);
        }*/

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
                    isChecked=false;
                    saveInSp("cec3",false);
                    saveInSp("cec1",false);
                    intent.putExtra("operacio", "pausa");
                    startService(intent);
                }else{
                    isChecked=true;
                    bubu1.setChecked(true);
                    saveInSp("cec1",true);
                    isChecked=true;
                    saveInSp("cec3",true);

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
                @Override
                public void onAudioFocusChange(int focusChange) {
                    switch (focusChange) {

                        case (AudioManager.AUDIOFOCUS_LOSS) :
                            isChecked=false;
                            bubu1.setChecked(false);
                            intent.putExtra("operacio", "pausa");
                            startService(intent);
                            am.abandonAudioFocus(focusChangeListener);

                            break;


                        case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) :
                            isChecked=false;
                            intent.putExtra("operacio", "stop");
                            startService(intent);
                            break;

                        case (AudioManager.AUDIOFOCUS_GAIN) :
                            isChecked=true;
                            bubu1.setChecked(true);
                            intent.putExtra("operacio", "inici");
                            startService(intent);
                            am.abandonAudioFocus(focusChangeListener);

                            break;

                        default: break;
                    }
                }
            };
}


