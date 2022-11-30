package com.example.planta;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;


public class ActivityPlanta extends Activity {

    static boolean iniciarHilo=true;
    boolean vida= true;
    //Contar horas y estado de crecimiento planta
    DetecionHora detecionHora= new DetecionHora();
    static int velocidadreloj=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planta);
        //start hilo
        iniciarHilo =true;
        vida=true;
        detecionHora.start();
        //cargar partida
        detecionHora.setX(empezar());
        //estado planta
        ImageView imagen = (ImageView) findViewById(R.id.imagen);
        TextView humedad =(TextView) findViewById(R.id.wet);
        TextView luz =(TextView) findViewById(R.id.luz);
        TextView acel =(TextView) findViewById(R.id.sped);
        TextView tenpe =(TextView) findViewById(R.id.temp);

        humedad.setText(R.string.humNo);
        acel.setText(R.string.shake);
        tenpe.setText(R.string.tempNo);

        //ajustes
        boolean nombrecien = getIntent().getBooleanExtra("cientifico", false);
        boolean realista= getIntent().getBooleanExtra("data",false);
        float velocidad= getIntent().getFloatExtra("velocidadFloat",1);
        boolean tempdes =getIntent().getBooleanExtra("temperatura", false);
        boolean humedadon=getIntent().getBooleanExtra("humedad", false);
        System.out.println(nombrecien);
        System.out.println(realista);


        System.out.println(velocidad);
        System.out.println(tempdes);
        System.out.println(humedadon);

        // tiempo
        int temporizador = 60;

        // damos nombre a la planta
        TextView nombre = (TextView) this.findViewById(R.id.nombrePlanta);
        nombre.setText(R.string.pimiento);

        // si nombrecien es true escribimos el nombre cientifico
        if (nombrecien) {
            TextView nombreCien = (TextView) this.findViewById(R.id.scientificName);
            nombreCien.setText("(Capsicum Annuum)");
        }

        // creamos el progressbar y depende el maximo del tiempo que tarda la planta
        ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.progressCrecimiento);
        progressBar.setMax((temporizador * 4)/(int)velocidad);

        // etapa de la planta ya le pondre texto de verdad e idiomas
        TextView etapa = (TextView) this.findViewById(R.id.etapaCrecimiento);
        etapa.setText("a");

        //manager
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        //planta
        Planta planta=  new Planta(20,500,8,25,-25);
        //-----------------------luz----------------------------//
        SensorEventListener luzSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(realista){
                    if(sensorEvent.values[0]<500){
                        luz.setText(R.string.luz1);
                    }else if(sensorEvent.values[0]>500&&sensorEvent.values[0]<1000){
                        luz.setText (R.string.luz2);
                    }else if(sensorEvent.values[0]>1000&&sensorEvent.values[0]<15000){
                        luz.setText(R.string.luz3);
                    }else if(sensorEvent.values[0]>15000){
                        luz.setText(R.string.luz4);
                    }
                }else{
                    luz.setText(String.valueOf(sensorEvent.values[0]));
                }

                if(sensorEvent.values[0]<planta.getLuz()){
                    kill();
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        Sensor light;
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        sm.registerListener
                (luzSensorEventListener, light, SensorManager.SENSOR_DELAY_NORMAL);
        //-----------------------wet----------------------------//
        SensorEventListener wetSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(humedadon){
                    if(realista){
                        if(sensorEvent.values[0]<20){
                            humedad.setText(R.string.hum1);
                        }else if(sensorEvent.values[0]>20&&sensorEvent.values[0]<40){
                            humedad.setText (R.string.hum2);
                        }else if(sensorEvent.values[0]>40&&sensorEvent.values[0]<80){
                            humedad.setText(R.string.hum3);
                        }else if(sensorEvent.values[0]>80){
                            humedad.setText(R.string.hum4);
                        }
                    }else{
                        humedad.setText(String.valueOf(sensorEvent.values[0]));
                    }
                    if((sensorEvent.values[0]<planta.getHumedad()) == humedadon ){
                        kill();
                    }
                }else {
                    humedad.setText(R.string.humNo);

                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        Sensor wet;
        wet = sm.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        sm.registerListener(wetSensorEventListener,wet,SensorManager.SENSOR_DELAY_NORMAL);
        //-----------------------temp----------------------------//
        SensorEventListener tempSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(tempdes) {
                    float temperatura = sensorEvent.values[0];
                    if (realista) {
                        if (sensorEvent.values[0] < -25) {
                            tenpe.setText(R.string.temp1);
                        } else if (sensorEvent.values[0] > -25 && sensorEvent.values[0] < 0) {
                            tenpe.setText(R.string.temp2);
                        } else if (sensorEvent.values[0] > 0 && sensorEvent.values[0] < 25) {
                            tenpe.setText(R.string.temp3);
                        } else if (sensorEvent.values[0] > 25) {
                            tenpe.setText(R.string.temp4);
                        }
                    } else {
                        tenpe.setText(String.valueOf(sensorEvent.values[0]));
                    }

                    if (temperatura < planta.tempmin || temperatura > planta.tempmax) {
                        kill();
                    }
                }else {
                    tenpe.setText(R.string.tempNo);
                }


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        Sensor temp;
        temp = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sm.registerListener(tempSensorEventListener,temp,SensorManager.SENSOR_DELAY_NORMAL);
        //-----------------------speed----------------------------//
        SensorEventListener speedSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(realista){
                    acel.setText(R.string.shake);
                }else{
                    acel.setText(String.valueOf(sensorEvent.values[0]));
                }
                if(sensorEvent.values[0]>planta.getAceleracion()){
                    kill();
                }

                // adaptamos el progressbar por segundo
                progressBar.setProgress(detecionHora.getX());

                if(detecionHora.getX() >= (temporizador/velocidad)&&detecionHora.getX()<= (temporizador*2/velocidad)&&vida){
                    imagen.setImageResource(R.drawable.etapa2);
                    etapa.setText("a"); // placeholder del nombre de la etapa
                }else if(detecionHora.getX() >= (temporizador*2/velocidad)&&detecionHora.getX()<= (temporizador*3/velocidad)&&vida){
                    imagen.setImageResource(R.drawable.etapa3);
                    etapa.setText("a"); // placeholder del nombre de la etapa
                }else if(detecionHora.getX() >= (temporizador*3/velocidad)&&detecionHora.getX()<= (temporizador*4/velocidad)&&vida){
                    imagen.setImageResource(R.drawable.etapa4);
                    etapa.setText("a"); // placeholder del nombre de la etapa
                }else if(detecionHora.getX() >= (temporizador*4/velocidad)&&vida){
                    imagen.setImageResource(R.drawable.etapa5);
                    etapa.setText("a"); // placeholder del nombre de la etapa
                    progressBar.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        Sensor speed;
        speed = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(speedSensorEventListener,speed,SensorManager.SENSOR_DELAY_NORMAL);


    }

    public void kill() {
        TextView status =(TextView) findViewById(R.id.vida);
        ImageView imagen = (ImageView) findViewById(R.id.imagen);
        ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.progressCrecimiento);
        vida = false;
        status.setText(R.string.dead);
        imagen.setImageResource(R.drawable.ded);
        progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        iniciarHilo = false;
        SharedPreferences sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("crecimiento",0);
        editor.commit();
    }

    public void guardar(View v){
        if(iniciarHilo){
            int x =detecionHora.getX();
            SharedPreferences sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("crecimiento",x);
            editor.commit();
        }
        finish();
    }
    public int empezar(){
        SharedPreferences sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        int x = sharedPreferences.getInt("crecimiento",0);
        return x;
    }

}