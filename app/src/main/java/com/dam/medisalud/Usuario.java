package com.dam.medisalud;

public class Usuario {
    private String nombres;
    private String apellidos;
    private String correo;
    private String id;
    private String uid;

    public Usuario() {
    }

    public Usuario(String nombres, String apellidos, String correo, String id, String uid) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.id = id;
        this.uid = uid;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return  nombres + apellidos + correo;
    }
}
