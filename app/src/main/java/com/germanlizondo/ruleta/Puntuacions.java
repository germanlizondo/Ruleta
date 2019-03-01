package com.germanlizondo.ruleta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Puntuacions extends AppCompatActivity {

    Database bd = new Database();
    ListView lista;
    ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    TextView titulo;
    ProgressBar progressBar;
    private MyBroadcastReceiver myReceiver;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacions);
    this.progressBar = (ProgressBar)findViewById(R.id.progressBar);
        this.lista = (ListView) findViewById(R.id.listaJugadors);
        this.titulo = (TextView) findViewById(R.id.tituloPuntuaciones);
        this.intent = new Intent(this, DatabaseService.class);
        this.mostrarJugadores();



    }


    public void mostrarJugadores(){


     /*   getDatabaseTask task = new getDatabaseTask();
        task.execute();
*/
        /*

        Intent intent = new Intent(this, DatabaseService.class);
        startService(intent);
        this.bd.getReference().child("Jugador").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Puntuacions.this.jugadores.clear();
                for (DataSnapshot x: dataSnapshot.getChildren()){

                    Jugador jugador = x.getValue(Jugador.class);
                    Puntuacions.this.jugadores.add(jugador);

                    }
                Collections.sort(Puntuacions.this.jugadores);

                AdaptadorPuntuaciones adapter = new AdaptadorPuntuaciones(Puntuacions.this,R.layout.item_puntuacions,Puntuacions.this.jugadores);
                Puntuacions.this.lista.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Puntuacions.this.titulo.setTextColor(Color.parseColor("#ff4d4d"));
                Puntuacions.this.titulo.setText("¡No hay conexión!");

            }
        });
        */
    }

    @Override
    protected void onResume() {

        startService(this.intent);

        super.onResume();
        myReceiver = new MyBroadcastReceiver();
        final IntentFilter intentFilter = new IntentFilter("MiAction");
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter);
    }

    @Override
    public void onPause(){
        super.onPause();
        if(myReceiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        myReceiver = null;
        stopService(this.intent);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        int x = 0;
        @Override
        public void onReceive(Context context, Intent intent) {

            if(x == 0){
                x++;
            }else{
                Bundle b = intent.getExtras();
                String yourValue = b.getString("nombre");
                Puntuacions.this.jugadores = b.getParcelableArrayList("jugadores");
                int saldo = b.getInt("saldo");
                Jugador jugador = new Jugador(yourValue,saldo);
               // Puntuacions.this.jugadores.add(jugador);

                Log.i("Puntuiaciones", Puntuacions.this.jugadores.size()+""+jugador.getNom());

                Collections.sort(Puntuacions.this.jugadores);

                AdaptadorPuntuaciones adapter = new AdaptadorPuntuaciones(Puntuacions.this,R.layout.item_puntuacions,Puntuacions.this.jugadores);
                Puntuacions.this.lista.setAdapter(adapter);
                Puntuacions.this.progressBar.setVisibility(Puntuacions.this.progressBar.INVISIBLE);
                x++;
            }




        }
    }

    private class getDatabaseTask extends AsyncTask<Void, Integer, Void> {

        private boolean acabado;

        /*
        Se hace visible el botón "Cancelar" y se desactiva
        el botón "Ordenar"
         */
        @Override
        protected void onPreExecute() {

            this.acabado =  false;

        }

        /*
        Ejecución del ordenamiento y transmision de progreso
         */
        @Override
        protected Void doInBackground(Void... params) {


            Puntuacions.this.bd.getReference().child("Jugador").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Puntuacions.this.jugadores.clear();
                    for (DataSnapshot x: dataSnapshot.getChildren()){

                        Jugador jugador = x.getValue(Jugador.class);
                        Puntuacions.this.jugadores.add(jugador);



                        AdaptadorPuntuaciones adapter = new AdaptadorPuntuaciones(Puntuacions.this,R.layout.item_puntuacions,Puntuacions.this.jugadores);
                        Puntuacions.this.lista.setAdapter(adapter);

                    }
                    Collections.sort(Puntuacions.this.jugadores);
                    Intent jugadoresIntent = new Intent("jugadores");
                    // You can also include some extra data.
                    jugadoresIntent.putExtra("jugadores", Puntuacions.this.jugadores);
                    LocalBroadcastManager.getInstance(Puntuacions.this).sendBroadcast(jugadoresIntent);
                    Puntuacions.this.progressBar.setVisibility(Puntuacions.this.progressBar.INVISIBLE);

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
