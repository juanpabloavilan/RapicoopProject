package co.edu.unipiloto.rapicoopproject.applicationcontext;

import co.edu.unipiloto.rapicoopproject.lib.ShoppingCart;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class ShoppingCartContext {
    private static ShoppingCartContext instance;
    private ShoppingCart shoppingCart;
    private  ShoppingCartContext(){
        shoppingCart = new ShoppingCart();
    }
    public static synchronized ShoppingCartContext getInstance(){
        if(instance == null){
            instance = new ShoppingCartContext();
        }
        return instance;
    }

    public void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
    }

    public ShoppingCart getShoppingCart(){
        return shoppingCart;
    }
}
