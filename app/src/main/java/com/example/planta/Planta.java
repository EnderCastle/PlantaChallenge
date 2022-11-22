package com.example.planta;

public class Planta {
    int humedad;
    int luz;
    int aceleracion;
    int tempmax;
    int tempmin;

    public Planta(int humedad, int luz, int aceleracion, int tempmax, int tempmin) {
        this.humedad = humedad;
        this.luz = luz;
        this.aceleracion = aceleracion;
        this.tempmax = tempmax;
        this.tempmin = tempmin;
    }
    public int getHumedad() {
        return humedad;
    }

    public void setHumedad(int humedad) {
        this.humedad = humedad;
    }

    public int getLuz() {
        return luz;
    }

    public void setLuz(int luz) {
        this.luz = luz;
    }

    public int getAceleracion() {
        return aceleracion;
    }

    public void setAceleracion(int aceleracion) {
        this.aceleracion = aceleracion;
    }

    public int getTempmax() {
        return tempmax;
    }

    public void setTempmax(int tempmax) {
        this.tempmax = tempmax;
    }

    public int getTempmin() {
        return tempmin;
    }

    public void setTempmin(int tempmin) {
        this.tempmin = tempmin;
    }
}
