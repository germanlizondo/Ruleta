package com.germanlizondo.ruleta;

import android.support.annotation.NonNull;

public class Jugador implements Comparable<Jugador> {


    private String nom;
    private int saldo;
    private Aposta aposta;

    public Jugador(){}

    public  Jugador(String nom, int saldo) {
        this.nom = nom;
        this.saldo = saldo;
        this.aposta = new Aposta();
    }

    @Override
    public String toString() {
        return "Jugador{" +
                ", nom='" + nom + '\'' +
                ", saldo=" + saldo +
                ", aposta=" + aposta +
                '}';
    }




    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public Aposta getAposta() {
        return aposta;
    }

    public void setAposta(Aposta aposta) {
        this.aposta = aposta;
    }

    @Override
    public int compareTo(@NonNull Jugador o) {
        if ( o.saldo < this.saldo ) return -1;
        else if ( o.saldo == this.saldo ) return 0;
        else return 1;
    }
}
