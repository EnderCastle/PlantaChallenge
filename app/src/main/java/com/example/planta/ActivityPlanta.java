package com.example.planta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

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
        //estado planta
        ImageView imagen = (ImageView) findViewById(R.id.imagen);
        TextView status =(TextView) findViewById(R.id.vida);
        TextView humedad =(TextView) findViewById(R.id.wet);
        TextView luz =(TextView) findViewById(R.id.luz);
        TextView acel =(TextView) findViewById(R.id.sped);
        TextView tenpe =(TextView) findViewById(R.id.temp);
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

        // damos nombre a la planta
        TextView nombre = (TextView) this.findViewById(R.id.nombrePlanta);
        nombre.setText("Pimiento");

        // si nombrecien es true escribimos el nombre cientifico
        if (nombrecien) {
            TextView nombreCien = (TextView) this.findViewById(R.id.scientificName);
            nombreCien.setText(":(Capsicum Annuum)");
        }

        // creamos el progressbar y depende el maximo del tiempo que tarda la planta
        ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.progressCrecimiento);
        progressBar.setMax(240/(int)velocidad);

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
                        luz.setText("Dark");
                    }else if(sensorEvent.values[0]>500&&sensorEvent.values[0]<1000){
                        luz.setText ("Few ilumination");
                    }else if(sensorEvent.values[0]>1000&&sensorEvent.values[0]<15000){
                        luz.setText("correct ilumination");
                    }else if(sensorEvent.values[0]>15000){
                        luz.setText("Flashbang");
                    }
                }else{
                    luz.setText(String.valueOf(sensorEvent.values[0]));
                }

                if(sensorEvent.values[0]<planta.getLuz()){
                    vida=false;
                    imagen.setImageResource(R.drawable.ded);
                status.setText("Dead");
                iniciarHilo = false;
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
                            humedad.setText("Desert like");
                        }else if(sensorEvent.values[0]>20&&sensorEvent.values[0]<40){
                            humedad.setText ("Dry");
                        }else if(sensorEvent.values[0]>40&&sensorEvent.values[0]<80){
                            humedad.setText("Nice humidity");
                        }else if(sensorEvent.values[0]>80){
                            humedad.setText("Get the mobile out of the water");
                        }
                    }else{
                        humedad.setText(String.valueOf(sensorEvent.values[0]));
                    }
                    if((sensorEvent.values[0]<planta.getHumedad()) == humedadon ){
                        vida=false;
                        status.setText("Dead");
                        imagen.setImageResource(R.drawable.ded);
                        iniciarHilo = false;
                    }
                }else {
                    humedad.setText("Humidity desactivated");

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
                            tenpe.setText("ICE");
                        } else if (sensorEvent.values[0] > -25 && sensorEvent.values[0] < 0) {
                            tenpe.setText("Cold");
                        } else if (sensorEvent.values[0] > 0 && sensorEvent.values[0] < 25) {
                            tenpe.setText("Warm");
                        } else if (sensorEvent.values[0] > 25) {
                            tenpe.setText("HOT");
                        }
                    } else {
                        tenpe.setText(String.valueOf(sensorEvent.values[0]));
                    }

                    if (temperatura < planta.tempmin || temperatura > planta.tempmax) {
                        vida = false;
                        status.setText("Dead");
                        imagen.setImageResource(R.drawable.ded);
                        iniciarHilo = false;
                    }
                }else {
                    tenpe.setText("Temperature desactivated");
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
                    acel.setText("Don't shake me");
                }else{
                    acel.setText(String.valueOf(sensorEvent.values[0]));
                }
                if(sensorEvent.values[0]>planta.getAceleracion()){
                    vida=false;
                    status.setText("Dead");
                    imagen.setImageResource(R.drawable.ded);
                    iniciarHilo = false;
                }

                // adaptamos el progressbar por segundo
                progressBar.setProgress(detecionHora.getX());

                if(detecionHora.getX() >= (60/velocidad)&&detecionHora.getX()<= (120/velocidad)&&vida){
                    imagen.setImageResource(R.drawable.etapa2);
                }else if(detecionHora.getX() >= (120/velocidad)&&detecionHora.getX()<= (180/velocidad)&&vida){
                    imagen.setImageResource(R.drawable.etapa3);
                }else if(detecionHora.getX() >= (180/velocidad)&&detecionHora.getX()<= (240/velocidad)&&vida){
                    imagen.setImageResource(R.drawable.etapa4);
                }else if(detecionHora.getX() >= (240/velocidad)&&vida){
                    imagen.setImageResource(R.drawable.etapa5);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        Sensor speed;
        speed = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(speedSensorEventListener,speed,SensorManager.SENSOR_DELAY_NORMAL);


        Date currentTime = Calendar.getInstance().getTime();
        TextView nacimiento = (TextView) findViewById(R.id.hora_start);
        nacimiento.setText(currentTime.toString());
    }

    public  void  test(View view){
        System.out.println("update"+detecionHora.getX());
    }

}