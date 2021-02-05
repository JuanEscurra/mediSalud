package com.dam.medisalud;

public class Medicamentox {
    private String nombrePastilla;
    private String fecha;
    private String cantidadDosis;
    private String horaDosis;
    private String id;
    public Medicamentox(){

    }

    public Medicamentox(String nombrePastilla, String fecha, String cantidadDosis, String horaDosis, String id) {
        this.nombrePastilla = nombrePastilla;
        this.fecha = fecha;
        this.cantidadDosis = cantidadDosis;
        this.horaDosis = horaDosis;
        this.id = id;
    }

    public String getNombrePastilla() {
        return nombrePastilla;
    }

    public void setNombrePastilla(String nombrePastilla) {
        this.nombrePastilla = nombrePastilla;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCantidadDosis() {
        return cantidadDosis;
    }

    public void setCantidadDosis(String cantidadDosis) {
        this.cantidadDosis = cantidadDosis;
    }

    public String getHoraDosis() {
        return horaDosis;
    }

    public void setHoraDosis(String horaDosis) {
        this.horaDosis = horaDosis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return fecha;
    }
}
