package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.rapicoopproject.ui_components.MenuAdapter;
import co.edu.unipiloto.rapicoopproject.ui_components.MenuDishCard;

public class ClientRestaurantMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_restaurant_menu);
        RecyclerView recyclerView = findViewById(R.id.rv_menu_dishes_list);
        List<MenuDishCard> listaPlatos = new ArrayList<>();

        listaPlatos.add(new MenuDishCard("Unos deliciosos perros calientes", "Super perro", "$9.99", R.drawable.hot_dog));
        listaPlatos.add(new MenuDishCard("Unos deliciosos perros calientes", "Super perro", "$9.99", R.drawable.hot_dog));
        listaPlatos.add(new MenuDishCard("Unos deliciosos perros calientes", "Super perro", "$9.99", R.drawable.hot_dog));
        listaPlatos.add(new MenuDishCard("Unos deliciosos perros calientes", "Super perro", "$9.99", R.drawable.hot_dog));
        listaPlatos.add(new MenuDishCard("Unos deliciosos perros calientes", "Super perro", "$9.99", R.drawable.hot_dog));
        listaPlatos.add(new MenuDishCard("Unos deliciosos perros calientes", "Super perro", "$9.99", R.drawable.hot_dog));

        MenuAdapter menuAdapter = new MenuAdapter(this, listaPlatos);
        recyclerView.setAdapter(menuAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}