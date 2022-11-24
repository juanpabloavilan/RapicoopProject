package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import co.edu.unipiloto.rapicoopproject.applicationcontext.ShoppingCartContext;
import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.entities.MenuDishFacade;
import co.edu.unipiloto.rapicoopproject.entities.OrderFacade;
import co.edu.unipiloto.rapicoopproject.lib.MenuDish;
import co.edu.unipiloto.rapicoopproject.lib.Order;
import co.edu.unipiloto.rapicoopproject.lib.ShoppingCart;
import co.edu.unipiloto.rapicoopproject.lib.User;
import co.edu.unipiloto.rapicoopproject.ui_components.MenuAdapter;

public class ShoppingCartActivity extends AppCompatActivity {
    private static final String TAG = "SHOPPING_CART_ACTIVITY";
    RecyclerView rvShoppingCartList;
    ShoppingCart shoppingCart;
    OrderFacade orderFacade;
    MenuDishFacade menuDishFacade;
    User userLogged;
    TextView tvShoppingCartVacio;
    ScrollView svListaShoppingCart;
    Button btnCrearOrden;
    TextView tvTotalPrice;
    int totalPrice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        orderFacade = OrderFacade.getInstance(this);
        menuDishFacade = MenuDishFacade.getInstance(this);
        userLogged = UserLoggedContext.getInstance().getUser();
        shoppingCart = ShoppingCartContext.getInstance().getShoppingCart();
        rvShoppingCartList = findViewById(R.id.rv_shopping_cart_list);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        btnCrearOrden = findViewById(R.id.btn_crear_orden);


        if(shoppingCart.size() == 0){
            mostrarShoppingCartVacio();
        }

        mostrarListaPlatos();
        calcularPrecioTotal();
        btnCrearOrden.setOnClickListener(v->{
            crearOrden();
        });

    }

    private void calcularPrecioTotal(){

    }

    private void crearOrden(){
        Toast.makeText(this, "Creando Orden", Toast.LENGTH_SHORT).show();
        //Order newOrder = new Order()
        //orderFacade.insertOrder(newOrder);
        //Para obtener id de restaurante de cada plato = shoppingCart.getRestauranteId(idPlato)
    }

    private void mostrarShoppingCartVacio(){
        tvShoppingCartVacio = findViewById(R.id.shopping_cart_vacio);
        tvShoppingCartVacio.setVisibility(View.VISIBLE);
    }

    private void mostrarListaPlatos(){
        svListaShoppingCart = findViewById(R.id.scrollView_lista_carrito);
        svListaShoppingCart.setVisibility(View.VISIBLE);
        Set<Integer> platosIdInShoppingCart = shoppingCart.getProductos().keySet();
        //Obtener info de platos de acuerdo al shopping cart
        List<MenuDish> listaPlatos = menuDishFacade.getMenuDishesByListOfIDs(platosIdInShoppingCart);
        MenuAdapter menuAdapter = new MenuAdapter(this, listaPlatos);
        menuAdapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {

            @Override
            public void onItemClickDecrease(int position) {

            }

            @Override
            public void onItemClickIncrease(int position) {

            }

            @Override
            public int getCurrentCantidad(int position) {
                MenuDish currentPlato = listaPlatos.get(position);
                return shoppingCart.getCantidadProducto(currentPlato.getId());
            }
        });

        rvShoppingCartList.setAdapter(menuAdapter);
        rvShoppingCartList.setLayoutManager(new LinearLayoutManager(this));
        totalPrice = 0;
        for (MenuDish plato: listaPlatos ) {
            int platoId = plato.getId();
            int precioProductoUnidad = plato.getPrecio();
            int cantidad = shoppingCart.getCantidadProducto(platoId);
            totalPrice += (precioProductoUnidad * cantidad);

        }
        String precioTotalFormattedAsCurrency = NumberFormat.getCurrencyInstance(new Locale("es","CO")).format(totalPrice);
        tvTotalPrice.setText(String.format("Precio total: %s", precioTotalFormattedAsCurrency));

    }

}