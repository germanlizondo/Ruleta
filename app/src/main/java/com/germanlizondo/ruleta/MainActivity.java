package com.germanlizondo.ruleta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



@Override
    public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menu_ruleta,mimenu);

        return true;

}

/*
MENU FUNCIONES
 */

@Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        int id = menuItem.getItemId();
        switch (id){
            case R.id.login:
                this.textView.setText("login");
                return true;

            case R.id.registro:
                this.abrirRegsitro();
                return true;

           default: return true;

        }

}


public void verPuntuaciones(View view){
    Intent puntuacionesIntent = new Intent(MainActivity.this,Puntuacions.class);

    startActivity(puntuacionesIntent);

}

public void abrirRegsitro(){
    Intent registroIntent = new Intent(MainActivity.this,Registro.class);

    startActivity(registroIntent);

}


    public void abrirPartida(View view){
        Intent partidaIntent = new Intent(MainActivity.this,Partida.class);

        startActivity(partidaIntent);

    }


}
