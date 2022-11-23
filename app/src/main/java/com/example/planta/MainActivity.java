package com.example.planta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boton = (Button) findViewById(R.id.buton_empezar);
        //ejemplo
        // hola, funciona?

    }
    public  void empezar (View v) {
        Intent intent = new Intent(getApplicationContext(),ActivityPlanta.class);
        startActivity(intent);
    }

    public void borrar (View v) {

    }

    public void ajustes (View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityAjustes.class);
        startActivityForResult(intent, 0);
    }
}