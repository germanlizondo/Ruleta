package com.germanlizondo.ruleta;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Partida extends AppCompatActivity {

    private Database bd = new Database();
    private static final String TAG = "PARTIDA";
    private Ruleta ruleta = new Ruleta();
    private  TextView textoGanador,textoPuntos,textoNombre;
    private EditText textoAposta,textPuntsAposta;
    private Aposta aposta;
    private Jugador jugador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        this.textoPuntos = (TextView) findViewById(R.id.puntos);
        this.textoNombre = (TextView) findViewById(R.id.nameJugador);
        this.textPuntsAposta = (EditText) findViewById(R.id.textPuntos);



       this.getJugador();




        this.textoGanador = (TextView) findViewById(R.id.textoGanador);
        this.textoAposta = (EditText) findViewById(R.id.textAposta);
     //   this.pintarRuleta();

    }


    public boolean validarAposta(){

        this.aposta = new Aposta();
        if(!this.textoAposta.getText().toString().equals("")){
            this.aposta.setNumero(Integer.parseInt(this.textoAposta.getText().toString()));
            if( this.aposta.getNumero() < 0 ||  this.aposta.getNumero() > 36  ){
                this.textoAposta.setError("Escriu un numero dintre de la ruleta");
              return false;
            }
            else {
                if(!this.textPuntsAposta.getText().toString().equals("")){
                    this.aposta.setQuantitat(Integer.parseInt(this.textPuntsAposta.getText().toString()));

                    if( this.aposta.getQuantitat() > 0 &&  this.aposta.getQuantitat() <= this.jugador.getSaldo() ){
                        return true;
                    }else{
                        this.textPuntsAposta.setError("No tens suficients punts");
                        return false;
                    }

                }else{
                    this.textPuntsAposta.setError("Fes una aposta");
                    return false;
                }

            }
        }else{
            return false;
        }
    }

    public void iniciarJoc(View view) {


if(this.validarAposta())
    this.girarRuleta();
    }




    public void girarRuleta(){
        Random random = new Random();


        this.textoGanador.setText("");


        Button apostaButton = (Button) findViewById(R.id.apostaButton);
        apostaButton.setEnabled(false);

        int low = 720;
        int high = 1440;

        int rnd = random.nextInt(high-low) + low;

        ImageView pilota = (ImageView) findViewById(R.id.ruletaImage);

        ObjectAnimator anim = ObjectAnimator.ofFloat(pilota, "rotation", 0f,
                rnd);
        anim.setDuration(5000);
        anim.start();
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {


            }


            @Override
            public void onAnimationEnd(Animator animation) {
                Button apostaButton = (Button) findViewById(R.id.apostaButton);

                super.onAnimationEnd(animation);
                Partida.this.mostrarResultat();
                Partida.this.comprobarResultat();
                apostaButton.setEnabled(true);
            }
            // Override other functions if we need
        });

    }

    public void mostrarResultat(){

        this.ruleta.seleccionarCasillaGanadora();
        this.textoGanador.setTextColor(Color.parseColor(this.ruleta.getCasillaGanadora().getColor()));
        this.textoGanador.setText(""+this.ruleta.getCasillaGanadora().getNumero());
    }


    public void comprobarResultat(){

        if(this.ruleta.getCasillaGanadora().getNumero() == this.aposta.getNumero()){

            this.jugador.setSaldo(this.aposta.getQuantitat()*35+this.jugador.getSaldo());
            this.modificarPuntos();
            Toast toast = Toast.makeText(this, "Guanyes", Toast.LENGTH_LONG);
            toast.show();
        }else{
            this.jugador.setSaldo(this.jugador.getSaldo()-this.aposta.getQuantitat());
            this.modificarPuntos();
            Toast toast = Toast.makeText(this, "Perds", Toast.LENGTH_LONG);
            toast.show();

        }

    }



    public void getJugador(){

        Bundle extras = getIntent().getExtras();
        String nomJugador = extras.getString("jugador");
        this.bd.getReference().child("Jugador").child(nomJugador).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Partida.this.jugador = dataSnapshot.getValue(Jugador.class);

                Partida.this.textoNombre.setText(Partida.this.jugador.getNom().toString());
                Partida.this.textoPuntos.setText(""+Partida.this.jugador.getSaldo()+" puntos");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void modificarPuntos(){
        this.bd.getReference().child("Jugador").child(this.jugador.getNom()).child("saldo").setValue(this.jugador.getSaldo());

    }



/*
    public void pintarRuleta(){

        RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.lienzoPintar);
        Lienzo ruletaPintada = new Lienzo(this);
        layout1.addView(ruletaPintada);
    }
    */
}
