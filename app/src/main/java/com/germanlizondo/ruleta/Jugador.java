package com.germanlizondo.ruleta;

public class Jugador {

    private int id;
    private String nom;
    private int saldo;
    private Aposta aposta;

    public Jugador(String nom, int saldo) {
        this.nom = nom;
        this.saldo = saldo;
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
}
