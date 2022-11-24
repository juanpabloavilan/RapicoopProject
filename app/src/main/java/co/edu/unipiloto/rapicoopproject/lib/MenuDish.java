package co.edu.unipiloto.rapicoopproject.lib;

import androidx.annotation.NonNull;

public class MenuDish {
    private int id;
    private String descripcion;
    private String nombre;
    private Integer precio;
    private int image;
    private String foodCategory;
    private int restaurantId;
    //private int cantidad = 0;

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

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
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
/*
    public int getCantidad(){
        return cantidad;
    }
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

    public void increaseCantidad(){
        this.cantidad++;
    }

    public void decreaseCantidad(){
        if(this.cantidad <= 0){
            return;
        }
        this.cantidad--;
    }
*/

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
                ", vendorId=" + restaurantId +
                '}';
    }
}
