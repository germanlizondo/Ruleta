package com.germanlizondo.ruleta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.pruebaTexto);

    }



@Override
    public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menu_ruleta,mimenu);

        return true;

}

/*
MENU FUNCIONES
 */

@Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        int id = menuItem.getItemId();
        switch (id){
            case R.id.comojugar:
                this.textView.setText("comojugar");
                return true;

            case R.id.puntuaciones:
                this.textView.setText("Puntuaciones");
                return true;

           default: return true;

        }

}


}
