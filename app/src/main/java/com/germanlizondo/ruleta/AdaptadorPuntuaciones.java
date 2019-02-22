package com.germanlizondo.ruleta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorPuntuaciones extends ArrayAdapter {

    private Context ctx;
    private int res;
    private ArrayList<Jugador> juagdores;

    public AdaptadorPuntuaciones(@NonNull Context context, int resource, @NonNull ArrayList<Jugador> objects) {
        super(context, resource, objects);

        this.ctx = context;
        this.res = resource;
        this.juagdores = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) convertView = LayoutInflater.from(this.ctx).inflate(this.res,null);


            Jugador jugador = this.juagdores.get(position);

            TextView nombre = (TextView) convertView.findViewById(R.id.nombreJugadorItem);
            nombre.setText(jugador.getNom());

            TextView puntos = (TextView) convertView.findViewById(R.id.puntosJugadorItem);
            puntos.setText(jugador.getSaldo()+" puntos");

            return convertView;



    }
}
