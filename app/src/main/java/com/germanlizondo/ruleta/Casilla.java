package com.germanlizondo.ruleta;

public class Casilla {
    private int numero;
    private String color;


    public Casilla(int numero, String color) {
        this.numero = numero;
        this.color = color;
    }


    @Override
    public String toString() {
        return "Casilla{" +
                "numero=" + numero +
                ", color='" + color + '\'' +
                '}';
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}
