package com.germanlizondo.ruleta;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class Ruleta {

    private ArrayList<Casilla> casillas = new ArrayList<Casilla>();
    private Casilla casillaGanadora;
    private ArrayList<Integer> numeros = new ArrayList<Integer>();
    private int numeroRnd;
    private int numeroCasillas = 37;

    public Ruleta() {

         this.rellenarRuleta();
    }



    private void rellenarRuleta() {
        Random rnd = new Random();
        int actualNumero = this.numeroCasillas;
        for (int z = 0; z < this.numeroCasillas;z++){
            this.numeros.add(z);
        }

        for (int x = 0; x < this.numeroCasillas;x++){


            this.numeroRnd = rnd.nextInt(actualNumero);

            if(this.numeroRnd != 0){
                 if(x % 2 == 0){
                     this.casillas.add(new Casilla(this.numeroRnd,"#d62121"));
                 }else if(x % 2 != 0){
                     this.casillas.add(new Casilla(this.numeroRnd,"#000000"));
                    }
            }else{
                 this.casillas.add(new Casilla(this.numeroRnd,"#16da3c"));
}


            actualNumero--;

            this.numeros.remove(this.numeroRnd);


        }
    }


    public void seleccionarCasillaGanadora(){

        Random rnd = new Random();

        this.numeroRnd = rnd.nextInt(37);

        this.casillaGanadora = this.casillas.get(this.numeroRnd);

        Log.d(TAG, "seleccionarCasillaGanadora: "+this.casillaGanadora);

    }



    public int devolverValorCasilla(int num){
        return this.casillas.get(num).getNumero();
    }
    public String devolverColorCasilla(int num){
        return this.casillas.get(num).getColor();
    }

    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }

    public void setCasillas(ArrayList<Casilla> casillas) {
        this.casillas = casillas;
    }

    public Casilla getCasillaGanadora() {
        return casillaGanadora;
    }

    public void setCasillaGanadora(Casilla casillaGanadora) {
        this.casillaGanadora = casillaGanadora;
    }

    public ArrayList<Integer> getNumeros() {
        return numeros;
    }

    public void setNumeros(ArrayList<Integer> numeros) {
        this.numeros = numeros;
    }

    public int getNumeroRnd() {
        return numeroRnd;
    }

    public void setNumeroRnd(int numeroRnd) {
        this.numeroRnd = numeroRnd;
    }
}
