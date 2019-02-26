package com.germanlizondo.ruleta;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Partida extends AppCompatActivity implements View.OnClickListener {

    private Database bd = new Database();
    private static final String TAG = "PARTIDA";
    private Ruleta ruleta = new Ruleta();
    private  TextView textoGanador,textoPuntos,textoNombre,textoApuesta;
    private EditText textPuntsAposta;
    private Aposta aposta;
    private Jugador jugador;
    private LinearLayout tapete;
    private Intent coinIntent;
    private ArrayList<CasillaTapete> casillasTapete = new ArrayList<CasillaTapete>();
    private LinearLayout casillaAnterior = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        this.textoPuntos = (TextView) findViewById(R.id.puntos);
        this.textoNombre = (TextView) findViewById(R.id.nameJugador);
        this.textPuntsAposta = (EditText) findViewById(R.id.textPuntos);
        this.tapete = (LinearLayout)findViewById(R.id.tapete);
        this.coinIntent = new Intent(this,CoinService.class);
        this.textoApuesta = (TextView) findViewById(R.id.textoApuesta);


       this.getJugador();




        this.textoGanador = (TextView) findViewById(R.id.textoGanador);




        this.crearTapete();
     //   this.pintarRuleta();

    }


    public boolean validarAposta(){

        this.aposta = new Aposta();
        if(!this.textoApuesta.getText().toString().equals("")){
            this.aposta.setNumero(Integer.parseInt(this.textoApuesta.getText().toString()));
            if( this.aposta.getNumero() < 0 ||  this.aposta.getNumero() > 36  ){
              Toast toast = Toast.makeText(this,"Escriu un numero dintre de la ruleta",Toast.LENGTH_LONG);
              toast.show();
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
            Toast toast = Toast.makeText(this,"Fes una aposta",Toast.LENGTH_LONG);
            toast.show();
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


    public void crearTapete(){

        int z = 0;

        Drawable d = getResources().getDrawable(R.drawable.tapete);

        LinearLayout.LayoutParams casillaParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        casillaParams.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams divParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);

        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rowParams.setMargins(20,20,20,20);





        LinearLayout num0 = new LinearLayout(this);
        num0.setOrientation(LinearLayout.VERTICAL);
        num0.setLayoutParams(rowParams);
        num0.setBackground(d);

        TextView num0Text = new TextView(this);


        num0Text.setPadding(10,10,10,10);
        num0Text.setLayoutParams(casillaParams);
        num0Text.setGravity(View.TEXT_ALIGNMENT_CENTER);

        num0Text.setText(z+"");


        num0.addView(num0Text);
        num0.setId(z);
        num0.setOnClickListener(this);


        this.tapete.addView(num0);

        this.casillasTapete.add(new CasillaTapete(z));



        for(int y = 0; y < 12;y++){



                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setLayoutParams(rowParams);

                for(int x = 0; x < 3;x++){
                    z++;
                    LinearLayout div = new LinearLayout(this);
                    div.setOrientation(LinearLayout.VERTICAL);
                    div.setLayoutParams(divParams);
                    div.setBackground(d);
                    div.setPadding(10,10,10,10);


                    TextView casilla = new TextView (this);

                    casilla.setPadding(10,10,10,10);
                    casilla.setLayoutParams(casillaParams);
                    casilla.setGravity(View.TEXT_ALIGNMENT_CENTER);

                    casilla.setText(z+"");


                    div.setId(z);
                    div.addView(casilla);
                    div.setOnClickListener(this);
                    row.addView(div);
                    this.casillasTapete.add(new CasillaTapete(z));




                }

                this.tapete.addView(row);



        }

    }

    @Override
    public void onClick(View v) {

        LinearLayout div = (LinearLayout) findViewById(v.getId());







        Drawable seleccionada = getResources().getDrawable(R.drawable.casillaseleccionada);
        Drawable noseleccionada = getResources().getDrawable(R.drawable.tapete);

        if( this.casillasTapete.get(v.getId()).isEstado()){



            div.setBackground(noseleccionada);

            this.casillasTapete.get(v.getId()).setEstado(!this.casillasTapete.get(v.getId()).isEstado());
            this.textoApuesta.setText("");



        }else {


            this.textoApuesta.setText(this.casillasTapete.get(v.getId()).getNumero() + "");

            div.setBackground(seleccionada);
            this.casillasTapete.get(v.getId()).setEstado(!this.casillasTapete.get(v.getId()).isEstado());
            startService(this.coinIntent);

            if(this.casillaAnterior != null){
                if (this.casillaAnterior.getId() != div.getId()) {
                    this.casillaAnterior.setBackground(noseleccionada);
                }


            }

            this.casillaAnterior = (LinearLayout) findViewById(v.getId());

        }


    }





/*
    public void pintarRuleta(){

        RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.lienzoPintar);
        Lienzo ruletaPintada = new Lienzo(this);
        layout1.addView(ruletaPintada);
    }
    */
}
