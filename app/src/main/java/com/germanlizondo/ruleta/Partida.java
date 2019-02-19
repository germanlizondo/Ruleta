package com.germanlizondo.ruleta;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Partida extends AppCompatActivity {

    private static final String TAG = "PARTIDA";
    private Ruleta ruleta = new Ruleta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

     //   this.pintarRuleta();

    }



    public void girarRuleta(View view) {

        Random random = new Random();

        TextView textoGanador = (TextView) findViewById(R.id.textoGanador);
        textoGanador.setText("");


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
                Partida.this.iniciarJoc();
                apostaButton.setEnabled(true);
            }
            // Override other functions if we need
        });


    }


    public void iniciarJoc(){

        TextView textoGanador = (TextView) findViewById(R.id.textoGanador);

        this.ruleta.seleccionarCasillaGanadora();
        textoGanador.setTextColor(Color.parseColor(this.ruleta.getCasillaGanadora().getColor()));
        textoGanador.setText(""+this.ruleta.getCasillaGanadora().getNumero());
    }




/*
    public void pintarRuleta(){

        RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.lienzoPintar);
        Lienzo ruletaPintada = new Lienzo(this);
        layout1.addView(ruletaPintada);
    }
    */
}
