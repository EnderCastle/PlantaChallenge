package com.example.planta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private boolean humedadOn;
    private boolean temperaturaOn;
    private boolean nombreOn;
    private String growSpeed;
    private float growSpeedFloat;
    private String data;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boton = (Button) findViewById(R.id.buton_empezar);

        // pillamos las variables del shared preference
        SharedPreferences sharedPreferences = getSharedPreferences("ajuste",Context.MODE_PRIVATE);
        humedadOn = sharedPreferences.getBoolean("humedad", false);
        temperaturaOn = sharedPreferences.getBoolean("temperatura", false);
        nombreOn = sharedPreferences.getBoolean("nombre", false);
        growSpeed = sharedPreferences.getString("velocidadCrecimiento","Normal");
        data = sharedPreferences.getString("data","Realistic");

    }
    public  void empezar (View v) {
        Intent intent = new Intent(getApplicationContext(),ActivityPlanta.class);
        intent.putExtra("humedad", humedadOn);
        intent.putExtra("temperatura", temperaturaOn);
        intent.putExtra("cientifico", nombreOn);
        intent.putExtra("velocidad", growSpeed);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    public void borrar (View v) {

    }

    public void ajustes (View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityAjustes.class);
        intent.putExtra("humedad", humedadOn);
        intent.putExtra("temperatura", temperaturaOn);
        intent.putExtra("cientifico", nombreOn);
        intent.putExtra("velocidad", growSpeed);
        intent.putExtra("data", data);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK && requestCode == 0) {
            humedadOn = intent.getBooleanExtra("humedad",false);
            temperaturaOn = intent.getBooleanExtra("temperatura", false);
            nombreOn = intent.getBooleanExtra("nombre", false);
            growSpeed = intent.getStringExtra("velocidad");
            data = intent.getStringExtra("dataDisplayed");

            // pillamos el valor float del string de velocidad
            switch (growSpeed) {
                case "slow":
                    growSpeedFloat = 1;
                    break;
                case "normal":
                    growSpeedFloat = 2;
                    break;
                case "fast":
                    growSpeedFloat = 3;
                    break;
            }

            // definimos el shared
            SharedPreferences sharedPreferences = getSharedPreferences("ajuste", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // guarda los ajustes en el shared
            editor.putBoolean("humedad", humedadOn);
            editor.putBoolean("temperatura", temperaturaOn);
            editor.putBoolean("nombre", nombreOn);
            editor.putString("velocidadCrecimiento", growSpeed);
            editor.putString("data", data);

            editor.commit();

        }
    }
}