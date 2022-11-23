package com.example.planta;

public class DetecionHora extends Thread{
    int x=0;
    public void run(){
        try {
            while (ActivityPlanta.iniciarHilo){
                Thread.sleep(1000);
                x++;
            }
        }catch (Exception e){
            System.out.println("Error de hilo ="+e.getMessage());
        }

    }

    public int getX() {
        return x;
    }
}
