package com.example.planta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private boolean humedadOn;
    private boolean temperaturaOn;
    private boolean nombreOn;
    private String growSpeed;
    private float growSpeedFloat = 2;
    private String data;
    private boolean databool;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String velocidad1 = getString(R.string.growSp1);
        String velocidad2 = getString(R.string.growSp2);
        String velocidad3 = getString(R.string.growSp3);

        String stat1 = getString(R.string.stat1);
        String stat2 = getString(R.string.stat2);

        // pillamos las variables del shared preference
        SharedPreferences sharedPreferences = getSharedPreferences("ajuste",Context.MODE_PRIVATE);
        humedadOn = sharedPreferences.getBoolean("humedad", false);
        temperaturaOn = sharedPreferences.getBoolean("temperatura", false);
        nombreOn = sharedPreferences.getBoolean("nombre", false);
        growSpeed = sharedPreferences.getString("velocidadCrecimiento","Normal");
        data = sharedPreferences.getString("data","Realistic");

        System.out.println(humedadOn +" "+ temperaturaOn +" "+ nombreOn +" "+ growSpeed +" "+ data);

        // pillamos el valor float del string de velocidad
        if (growSpeed.equals(velocidad1)) {
            growSpeedFloat = 1;
        }
        else if (growSpeed.equals(velocidad2)) {
            growSpeedFloat = 2;
        }
        else if (growSpeed.equals(velocidad3)) {
            growSpeedFloat = 3;
        }

        //pillamos el valor boolean de realismo/estandar
        if (data.equals(stat1)) {
            databool = true;
        }
        else if (data.equals(stat2)) {
            databool = false;
        }

        System.out.println(growSpeedFloat + " " + databool);

    }
    public  void empezar (View v) {
        Intent intent = new Intent(getApplicationContext(),ActivityPlanta.class);
        intent.putExtra("humedad", humedadOn);
        intent.putExtra("temperatura", temperaturaOn);
        intent.putExtra("cientifico", nombreOn);
        intent.putExtra("velocidadFloat", growSpeedFloat);
        intent.putExtra("data", databool);
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