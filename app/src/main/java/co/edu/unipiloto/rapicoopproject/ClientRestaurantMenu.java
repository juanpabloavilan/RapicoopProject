package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.rapicoopproject.applicationcontext.ShoppingCartContext;
import co.edu.unipiloto.rapicoopproject.entities.MenuDishFacade;
import co.edu.unipiloto.rapicoopproject.lib.MenuDish;
import co.edu.unipiloto.rapicoopproject.lib.ShoppingCart;
import co.edu.unipiloto.rapicoopproject.lib.User;
import co.edu.unipiloto.rapicoopproject.ui_components.MenuAdapter;

public class ClientRestaurantMenu extends AppCompatActivity {
    private static final String TAG = "CLIENT_RESTAURANT_MENU";
    public static final String ID_RESTAURANTE = "id_restaurante";
    private RecyclerView rvListaPlatosMenu;
    private User userLogged;
    private MenuAdapter menuAdapter;
    private ShoppingCart shoppingCart;
    private MenuDishFacade menuDishFacade;
    private int idRestaurante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_restaurant_menu);
        idRestaurante = getIntent().getIntExtra(ID_RESTAURANTE, 1);
        rvListaPlatosMenu = findViewById(R.id.rv_menu_dishes_list);
        shoppingCart = ShoppingCartContext.getInstance().getShoppingCart();
        menuDishFacade = MenuDishFacade.getInstance(this);
        loadMenuRestaurante();
    }

    private void  loadMenuRestaurante(){
        List<MenuDish> listaPlatos = menuDishFacade.getMenuDishesByVendorID(idRestaurante); //Platos Facade
        if(listaPlatos == null){
            return;
        }
        if(listaPlatos.size() == 0){
            return;
        }
        /*listaPlatos.add(new MenuDish(1,"Unos deliciosos perros calientes", "Super perro", 9999, R.drawable.hot_dog));
        listaPlatos.add(new MenuDish(2,"Hamburguesa con to do", "Hamburquesa", 9999, R.drawable.hot_dog));
        listaPlatos.add(new MenuDish(3,"Sandwich de pavo", "Sandwich", 9999, R.drawable.hot_dog));
        listaPlatos.add(new MenuDish(4,"Pollo frito", "Pollo", 9999, R.drawable.hot_dog));
        listaPlatos.add(new MenuDish(5,"Helado Sundae", "Helado", 222222, R.drawable.hot_dog));
        listaPlatos.add(new MenuDish(6,"Waffles con helado", "Waffles", 1221212, R.drawable.hot_dog));*/

        MenuAdapter menuAdapter = new MenuAdapter(this, listaPlatos);
        menuAdapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClickDecrease(int position) {
                MenuDish currentPlato = listaPlatos.get(position);
                //Persistir cambio en carrito
                shoppingCart.removeOrDecreaseProduct(currentPlato.getId());
                Log.v(TAG, String.format("SHOPPING CART = %s", shoppingCart.toString()));

            }

            @Override
            public void onItemClickIncrease(int position) {
                MenuDish currentPlato = listaPlatos.get(position);
                //Persistir cambio en carrito
                shoppingCart.addOrIncreaseProduct(currentPlato.getId(), idRestaurante);
                Log.v(TAG, String.format("SHOPPING CART = %s", shoppingCart.toString()));

            }

            @Override
            public int getCurrentCantidad(int position) {
                MenuDish currentPlato = listaPlatos.get(position);
                return shoppingCart.getCantidadProducto(currentPlato.getId());

            }
        });
        rvListaPlatosMenu.setAdapter(menuAdapter);
        rvListaPlatosMenu.setLayoutManager(new LinearLayoutManager(this));


    }
}