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
    private Boolean estaReproduint = false;

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

    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);

    }



    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i("MUSICA","OnCommand jipikajei");
        if (intent != null) {
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

}
