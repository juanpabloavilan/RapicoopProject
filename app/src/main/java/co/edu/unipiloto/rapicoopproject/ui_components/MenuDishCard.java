package co.edu.unipiloto.rapicoopproject.ui_components;

public class MenuDishCard {
    private String descripcion;
    private String nombre;
    private String precio;
    private int picture;

    public MenuDishCard(String descripcion, String nombre, String precio, int picture) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.precio = precio;
        this.picture = picture;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}
