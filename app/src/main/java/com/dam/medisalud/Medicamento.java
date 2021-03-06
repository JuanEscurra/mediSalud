package com.dam.medisalud;

public class Medicamento {
    private String nombrePastilla;
    private String fecha;
    private String cantidadDosis;
    private String horaDosis;
    private String id;
    private String uid;
    public Medicamento(){

    }

    public Medicamento(String nombrePastilla, String fecha, String cantidadDosis, String horaDosis, String id,String uid) {
        this.nombrePastilla = nombrePastilla;
        this.fecha = fecha;
        this.cantidadDosis = cantidadDosis;
        this.horaDosis = horaDosis;
        this.id = id;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
        return
                nombrePastilla + " "+ cantidadDosis;
    }

}
