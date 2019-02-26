package com.germanlizondo.ruleta;

public class CasillaTapete {
    private int numero;
    private boolean estado;

    public CasillaTapete(int numero) {
        this.numero = numero;
        this.estado = false;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
