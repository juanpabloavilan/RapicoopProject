package co.edu.unipiloto.rapicoopproject.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private Map<Integer, Map<String, Integer>> productos; //{Id Producto: {cantidad: 2, idRestaurante:5}}

    public ShoppingCart() {
        productos = new HashMap<>();
    }

    public void addOrIncreaseProduct(int idProducto, int idRestaurante){
        if(productos.containsKey(idProducto)){
            HashMap<String, Integer> values = (HashMap<String, Integer>) productos.get(idProducto);
            boolean hasCantidad = values.containsKey("cantidad");
            boolean hasRestauranteId = values.containsKey("restauranteId");
            if(hasCantidad && hasRestauranteId){
                int prevCantidad = values.get("cantidad");
                productos.get(idProducto).put("cantidad", prevCantidad+1);
            }
        }else {
            HashMap<String, Integer> values = new HashMap<>();
            values.put("cantidad", 1);
            values.put("restauranteId", idRestaurante);
            productos.put(idProducto,values);
        }
    }

    public int size(){
        return this.productos.size();
    }

    public Map<Integer, Map<String, Integer>> getProductos() {
        return productos;
    }

    public void removeOrDecreaseProduct(int idProducto ){
        if(productos.containsKey(idProducto)){
            HashMap<String, Integer> values = (HashMap<String, Integer>) productos.get(idProducto);
            boolean hasCantidad = values.containsKey("cantidad");
            boolean hasRestauranteId = values.containsKey("restauranteId");
            if(hasCantidad && hasRestauranteId){
                int prevCantidad = values.get("cantidad");
                if(prevCantidad <= 1){
                    productos.remove(idProducto);
                }else{
                    productos.get(idProducto).put("cantidad", prevCantidad-1);
                }

            }
        }
    }

    public int getCantidadProducto(int idProducto){
        if(!productos.containsKey(idProducto)) return 0;
        return productos.get(idProducto).get("cantidad");
    }

    public int getRestauranteId(int idProducto){
        if(!productos.containsKey(idProducto)) return 0;
        return productos.get(idProducto).get("restauranteId");
    }

    @Override
    public String toString() {
        StringBuilder productosToString = new StringBuilder();
        for (Integer i : productos.keySet()) {
            productosToString.append("key: ").append(i).append(" value: \n")
                    .append("Cantidad: ").append(productos.get(i).get("cantidad")).append(" \n")
                    .append("IdRestaurante: ").append(productos.get(i).get("restauranteId")).append("\n");
        }
        return productosToString.toString();
    }
}
