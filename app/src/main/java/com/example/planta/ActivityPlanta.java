package com.example.planta;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityPlanta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planta);
        //estado planta
        TextView status =(TextView) findViewById(R.id.vida);
        TextView humedad =(TextView) findViewById(R.id.wet);
        TextView luz =(TextView) findViewById(R.id.luz);
        TextView acel =(TextView) findViewById(R.id.sped);
        TextView tenpe =(TextView) findViewById(R.id.temp);
        //manager
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        //planta
        Planta planta=  new Planta(20,10000,12,25,-25);

        //-----------------------luz----------------------------//
        SensorEventListener luzSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                luz.setText(String.valueOf(sensorEvent.values[0]));
                if(sensorEvent.values[0]<planta.getLuz()){
                status.setText("Muerta");
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
                humedad.setText(String.valueOf(sensorEvent.values[0]));
                if(sensorEvent.values[0]<planta.getHumedad()){
                    status.setText("Muerta");
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
                float temperatura= sensorEvent.values[0];
                tenpe.setText(String.valueOf(sensorEvent.values[0]));
                if(temperatura<planta.tempmin && temperatura>planta.tempmax){
                    status.setText("Muerta");
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
                acel.setText(String.valueOf(sensorEvent.values[0]));
                if(sensorEvent.values[0]<planta.getAceleracion()){
                    status.setText("Muerta");
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
}