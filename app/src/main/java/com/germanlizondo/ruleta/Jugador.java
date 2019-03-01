package com.germanlizondo.ruleta;

import android.graphics.ColorSpace;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Jugador implements Comparable<Jugador>,Parcelable  {



    private String nom;
    private int saldo;
    private Aposta aposta;

    public Jugador(){}

    public  Jugador(String nom, int saldo) {
        this.nom = nom;
        this.saldo = saldo;
        this.aposta = new Aposta();
    }

    private Jugador(Parcel source) {
        this.nom = source.readString();
        this.saldo = source.readInt();

    }

    public static final Parcelable.Creator<Jugador> CREATOR = new Parcelable.Creator<Jugador>() {

        @Override
        public Jugador createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Jugador[] newArray(int size) {
            return new Jugador[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nom);
        dest.writeInt(this.saldo);
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
