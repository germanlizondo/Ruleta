package com.germanlizondo.ruleta;

import android.app.IntentService;
import android.content.Intent;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Jugador jugador = new Jugador();
    private Button musicButton;
    private Intent intentMusic;
    private boolean isReproduint= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.intentMusic = new Intent(this,AudioService.class);


        setContentView(R.layout.activity_main);


        this.musicButton = (Button) findViewById(R.id.audioButton);

        this.textView = (TextView) findViewById(R.id.tituloMain);



        try{
            this.verJugadorLogeado();

        }catch (Exception e){

        }

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
                this.abrirLogin();
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

    public void abrirLogin(){
        Intent loginIntent = new Intent(MainActivity.this,Login.class);

        startActivity(loginIntent);

    }



    public void abrirPartida(View view){

   if(this.jugador.getNom() == null){

       Toast toast = Toast.makeText(this, "Logejat abans de jugar!", Toast.LENGTH_LONG);
       toast.show();

   }else{
       Intent partidaIntent = new Intent(MainActivity.this,Partida.class);
       partidaIntent.putExtra("jugador",this.jugador.getNom());

       startActivity(partidaIntent);
   }


    }


    public void verJugadorLogeado(){
        Bundle extras = getIntent().getExtras();
        this.jugador.setNom(extras.getString("jugador"));
        this.textView.setText("Benvingut "+this.jugador.getNom());

    }


    public void stadeMusic(View view){

    if(!this.isReproduint){
        Log.i("MUSICA","Reproduint");
        this.musicButton.setText("Musica Reproduint");
        this.intentMusic.putExtra("operacio","inici");
        startService(this.intentMusic);
        this.isReproduint = !this.isReproduint;
    }else{
        Log.i("MUSICA","Pausat");
        this.musicButton.setText("Musica Pausada");
        this.intentMusic.putExtra("operacio","pausa");
        startService(this.intentMusic);
        this.isReproduint = !this.isReproduint;
    }


    }


}
