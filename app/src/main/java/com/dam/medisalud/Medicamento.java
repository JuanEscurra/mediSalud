package com.dam.medisalud;

public class Medicamento {
    private String pastilla;
    private String fecha;
    public Medicamento(){

    }
    public Medicamento(String pastilla,String fecha){
        this.pastilla = pastilla;
        this.fecha = fecha;
    }

    public String getPastilla() {
        return pastilla;
    }

    public void setPastilla(String pastilla) {
        this.pastilla = pastilla;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
