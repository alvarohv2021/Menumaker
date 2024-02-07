package com.example.menumaker;

public class Plato {

    private String nombrePlato;
    private String nombreCategoria;

    public Plato(String categoria, String plato) {
        this.nombreCategoria = categoria;
        this.nombrePlato = plato;
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
}
