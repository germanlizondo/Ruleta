package com.germanlizondo.ruleta;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class DatabaseService extends Service {
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private getDatabaseTask asyncDatabase;
    static final int MSG_SAY_HELLO = 1;


    public DatabaseService() {
    }

    @Override
    public void onCreate() {

        super.onCreate();


        this.database = FirebaseDatabase.getInstance();
        this.reference = database.getReference();
        this.asyncDatabase = new getDatabaseTask();
        this.asyncDatabase.execute();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        this.asyncDatabase.onCancelled();
        super.onDestroy();
    }
    final Messenger mMessenger = new Messenger(new IncomingHandler());
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
        return mMessenger.getBinder();
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }


    public void agregarJugador(Jugador jugador){
        this.reference.child("Jugador").child(jugador.getNom()).setValue(jugador);
    }


    private class getDatabaseTask extends AsyncTask<Void, Integer, Void> {

        Intent intentFirebase;
        Bundle bundleFirebase;
        /*
        Se hace visible el botón "Cancelar" y se desactiva
        el botón "Ordenar"
         */
        @Override
        protected void onPreExecute() {

            intentFirebase = new Intent("MiAction");
            bundleFirebase = new Bundle();
            bundleFirebase.putString("jugadores","HELLO THERE SUUUUUUUUUUUU"); // put extras you want to pass with broadcast. This is optional
            intentFirebase.putExtras(bundleFirebase);
            LocalBroadcastManager.getInstance(DatabaseService.this).sendBroadcast(intentFirebase);


        }

        /*
        Ejecución del ordenamiento y transmision de progreso
         */
        @Override
        protected Void doInBackground(Void... params) {


            DatabaseService.this.reference.child("Jugador").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int y = 0;
                    DatabaseService.this.jugadores.clear();
                    for (DataSnapshot x: dataSnapshot.getChildren()){

                        Jugador jugador = x.getValue(Jugador.class);
                        DatabaseService.this.jugadores.add(jugador);
                        Log.i("HGERGREGERGEGERGRERE", DatabaseService.this.jugadores.size()+""+DatabaseService.this.jugadores.get(y).getNom());
                        bundleFirebase.putParcelableArrayList("jugadores",DatabaseService.this.jugadores);
                        bundleFirebase.putString("nombre",jugador.getNom());
                        bundleFirebase.putInt("saldo",jugador.getSaldo());
                        intentFirebase.putExtras(bundleFirebase);
                        LocalBroadcastManager.getInstance(DatabaseService.this).sendBroadcast(intentFirebase);

                        Toast.makeText(DatabaseService.this,"hello "+DatabaseService.this.jugadores.size(),Toast.LENGTH_LONG);

                        y++;
                    }
                    Collections.sort(DatabaseService.this.jugadores);



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {


                }
            });

            return null;
        }

        /*
         Se informa en progressLabel que se canceló la tarea y
         se hace invisile el botón "Cancelar"
          */
        @Override
        protected void onCancelled() {
            super.onCancelled();

        }

        /*
        Impresión del progreso en tiempo real
          */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }



    }


}
