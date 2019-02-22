package com.germanlizondo.ruleta;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.germanlizondo.ruleta.Jugador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Database {

    private FirebaseDatabase database;
    private  DatabaseReference reference;
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();


    public Database() {
        this.database = FirebaseDatabase.getInstance();
        this.reference = database.getReference();
    }




    public void agregarJugador(Jugador jugador){
        this.reference.child("Jugador").child(jugador.getNom()).setValue(jugador);
    }




    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void setDatabase(FirebaseDatabase database) {
        this.database = database;
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public void setReference(DatabaseReference reference) {
        this.reference = reference;
    }
}
