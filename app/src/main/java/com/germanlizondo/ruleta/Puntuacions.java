package com.germanlizondo.ruleta;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacions);

        this.lista = (ListView) findViewById(R.id.listaJugadors);
        this.titulo = (TextView) findViewById(R.id.tituloPuntuaciones);

        this.mostrarJugadores();



    }

    public void mostrarJugadores(){
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
    }
}
