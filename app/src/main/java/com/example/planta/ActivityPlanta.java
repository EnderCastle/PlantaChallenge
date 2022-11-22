package com.example.planta;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class ActivityPlanta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planta);
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        //-----------------------luz----------------------------//
        SensorEventListener luzSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {


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
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        Sensor temp;
        temp = sm.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        sm.registerListener(tempSensorEventListener,temp,SensorManager.SENSOR_DELAY_NORMAL);
        //-----------------------speed----------------------------//
        SensorEventListener speedSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        Sensor speed;
        speed = sm.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        sm.registerListener(speedSensorEventListener,speed,SensorManager.SENSOR_DELAY_NORMAL);


    }
}