package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.rapicoopproject.lib.MenuDish;
import co.edu.unipiloto.rapicoopproject.ui_components.MenuAdapter;

public class ClientRestaurantMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_restaurant_menu);
        RecyclerView recyclerView = findViewById(R.id.rv_menu_dishes_list);
        List<MenuDish> listaPlatos = new ArrayList<>();

        listaPlatos.add(new MenuDish("Unos deliciosos perros calientes", "Super perro", 9999, R.drawable.hot_dog));
        listaPlatos.add(new MenuDish("Unos deliciosos perros calientes", "Super perro", 9999, R.drawable.hot_dog));
        listaPlatos.add(new MenuDish("Unos deliciosos perros calientes", "Super perro", 9999, R.drawable.hot_dog));
        listaPlatos.add(new MenuDish("Unos deliciosos perros calientes", "Super perro", 9999, R.drawable.hot_dog));
        listaPlatos.add(new MenuDish("Unos deliciosos perros calientes", "Super perro", 222222, R.drawable.hot_dog));
        listaPlatos.add(new MenuDish("Unos deliciosos perros calientes", "Super perro", 1221212, R.drawable.hot_dog));

        MenuAdapter menuAdapter = new MenuAdapter(this, listaPlatos);
        recyclerView.setAdapter(menuAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}