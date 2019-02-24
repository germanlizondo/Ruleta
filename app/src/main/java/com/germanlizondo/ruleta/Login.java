package com.germanlizondo.ruleta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText texto;
    Database bd = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.texto = (EditText) findViewById(R.id.nameLogin);
    }


    public void hacerLogin(View view){

        String name = this.texto.getText().toString();
        try{
            this.bd.getReference().child("Jugador").child(name).child("nom").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    String nom = dataSnapshot.getValue().toString();
                    Intent intent = new Intent(Login.this,MainActivity.class);

                    Jugador jugador = new Jugador();
                    jugador.setNom(nom);

                    intent.putExtra("jugador",jugador.getNom());
                    startActivity(intent);
                }catch (Exception e){
                    Toast toast = Toast.makeText(Login.this, "No hi ha aquest jugador", Toast.LENGTH_LONG);
                    toast.show();
                    Login.this.texto.setText(" ");
                }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast toast = Toast.makeText(Login.this, "No hi ha aquest jugador", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }catch (Exception e){
            Toast toast = Toast.makeText(Login.this, "No hi ha aquest jugador", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
