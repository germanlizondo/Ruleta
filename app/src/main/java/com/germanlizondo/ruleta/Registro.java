package com.germanlizondo.ruleta;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Registro extends AppCompatActivity {

    private FirebaseApp firebaseDatabase;

    private String nombre;
    private EditText textonombre;

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
        }

    }


}
