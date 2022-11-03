package co.edu.unipiloto.rapicoopproject.lib;

import androidx.annotation.NonNull;

public class MenuDish {
    private int id;
    private String descripcion;
    private String nombre;
    private Integer precio;
    private int image;
    private String foodCategory;
    private int vendorId;

    public MenuDish(String descripcion, String nombre, int precio, int image) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.precio = precio;
        this.image = image;
    }
    public MenuDish(int id, String descripcion, String nombre, int precio, int image) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.precio = precio;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
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

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image= image;
    }

    @NonNull
    @Override
    public String toString() {
        return "MenuDish{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", image=" + image +
                ", foodCategory='" + foodCategory + '\'' +
                ", vendorId=" + vendorId +
                '}';
    }
}
