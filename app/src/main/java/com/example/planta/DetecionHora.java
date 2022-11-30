package com.example.planta;

import android.app.Activity;
import android.widget.ImageView;

public class DetecionHora extends Thread {
    int x=0;
    public void run(){
        try {
            while (ActivityPlanta.iniciarHilo){
                Thread.sleep(1000);
                x+=ActivityPlanta.velocidadreloj;
            }
        }catch (Exception e){
            System.out.println("Error de hilo ="+e.getMessage());
        }

    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

}
