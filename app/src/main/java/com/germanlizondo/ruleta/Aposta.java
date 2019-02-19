package com.germanlizondo.ruleta;

public class Aposta {

    private int numero;
    private int color;

    public Aposta(int numero, int color) {
        this.numero = numero;
        this.color = color;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
