package com.germanlizondo.ruleta;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {

    private static final String TAG = "PRUEBA" ;
    private FirebaseApp firebaseDatabase;

    private String nombre;
    private EditText textonombre;
    private Database db = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        this.textonombre = (EditText) findViewById(R.id.nameRegistro);
    }


    public void registrarUsuario(View view){
        this.nombre = this.textonombre.getText().toString();

        if(this.nombre.equals("")){
            this.textonombre.setError("Required");
        }else{
            Jugador jugador = new Jugador(this.textonombre.getText().toString(),1000);

            Log.i(TAG, "registrarUsuario: "+jugador);

            this.db.agregarJugador(jugador);

            this.textonombre.setText("");

            Intent loginIntent = new Intent(this,Login.class);

            startActivity(loginIntent);
        }

    }


}
