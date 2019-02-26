package com.germanlizondo.ruleta;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class AudioService  extends IntentService {

    private MediaPlayer mp;
    private MediaPlayer coinmp;
    private Boolean estaReproduint = false;
    private AudioManager am;

    public AudioService(){
        super("AudioService");
    }



    @Override
    public void onCreate() {
        super.onCreate();


        mp = MediaPlayer.create(this, R.raw.deathletter);
        mp.setLooping(true);
        mp.setVolume(100,100);
        Log.i("MUSICA","Serice Create");

        this.am = (AudioManager) getSystemService(AUDIO_SERVICE);


    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);

    }



    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {


        if (intent != null) {


            int requestResult = this.am.requestAudioFocus(
                    mAudioFocusListener, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN);

            if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mp.start();

                Log.d("HELLO THERE", "audioFocus listener aconseguit amb èxit");

            } else if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
                mp.stop();
            } else {
                Log.d("HELLO THERE", "error en la petició del listener de focus ");
            }

            String operacio = intent.getStringExtra("operacio");
            Log.w("MUSICA",operacio);
            switch (operacio){
                case "inici" : this.mp.start();this.estaReproduint = !this.estaReproduint;
                    break;
                case "pausa" : this.mp.pause(); this.estaReproduint = !this.estaReproduint;
                    break;
                default:
                    break;
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mp.stop();
    }

    private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {

            switch (focusChange) {
                //perdem el focus per exemple, una altre reproductor de música
                case AudioManager.AUDIOFOCUS_LOSS:
                    mp.pause();
                    Log.d("HELLO THERE AUDIO", "AudioFocus: rebut AUDIOFOCUS_LOSS");


                    break;
                //perdem el focus temporalement, per exemple, trucada
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    if (mp.isPlaying())
                        mp.pause();

                    Log.d("HELLO THERE AUDIO", "AudioFocus: rebut AUDIOFOCUS_LOSS_TRANSIENT");

                    break;
                //baixem el volum temporalment
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mp.setVolume(0.5f, 0.5f);
                    Log.d("HELLO THERE AUDIO", "AudioFocus: rebut AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    break;

                //es recupera el focus d'audio
                case AudioManager.AUDIOFOCUS_GAIN:
                    mp.start();
                    mp.setVolume(1.0f, 1.0f);
                    Log.d("HELLO THERE AUDIO", "AudioFocus: rebut AUDIOFOCUS_GAIN");
                    break;

                default:
                    Log.e("HELLO THERE AUDIO", "codi desconegut");
            }
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

}
