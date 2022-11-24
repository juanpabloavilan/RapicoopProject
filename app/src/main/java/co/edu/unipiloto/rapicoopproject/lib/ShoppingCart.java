package co.edu.unipiloto.rapicoopproject.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private Map<Integer, Integer> productos; //Id Producto, cantidad

    public ShoppingCart() {
        productos = new HashMap<>();
    }

    public void addOrIncreaseProduct(int idProducto ){
        if(productos.containsKey(idProducto)){
            productos.put(idProducto, productos.get(idProducto) +1);
        }else {
            productos.put(idProducto, 1);
        }
    }

    public int size(){
        return this.productos.size();
    }

    public Map<Integer, Integer> getProductos() {
        return productos;
    }

    public void removeOrDecreaseProduct(int idProducto ){
        if(!productos.containsKey(idProducto)) return;
        int currentCantidad = productos.get(idProducto);
        if(currentCantidad == 1){
            productos.remove(idProducto);
        }else {
            productos.put(idProducto, productos.get(idProducto) -1);
        }
    }

    public int getCantidadProducto(int idProducto){
        if(!productos.containsKey(idProducto)) return 0;
        return productos.get(idProducto);
    }

    @Override
    public String toString() {
        StringBuilder productosToString = new StringBuilder();
        for (Integer i : productos.keySet()) {
            productosToString.append("key: ").append(i).append(" value: ").append(productos.get(i)).append(" \n");
        }
        return productosToString.toString();
    }
}
