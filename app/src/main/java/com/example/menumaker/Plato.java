package com.example.menumaker;

public class Plato {

    private String nombrePlato;
    private String nombreCategoria;

    private String idPlato;

    public Plato(String categoria, String plato, String idPlato) {
        this.nombreCategoria = categoria;
        this.nombrePlato = plato;
        this.idPlato = idPlato;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getIdPlato() {
        return this.idPlato;
    }
}
