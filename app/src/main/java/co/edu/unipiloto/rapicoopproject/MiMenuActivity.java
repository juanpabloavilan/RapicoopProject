package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.entities.MenuDishFacade;
import co.edu.unipiloto.rapicoopproject.lib.MenuDish;
import co.edu.unipiloto.rapicoopproject.lib.User;
import co.edu.unipiloto.rapicoopproject.ui_components.MenuAdapter;

public class MiMenuActivity extends AppCompatActivity {
    MenuDishFacade menuDishFacade;
    RecyclerView rvListaPlatos;
    MenuAdapter menuAdapter;
    User userLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_menu);
        menuDishFacade = MenuDishFacade.getInstance(this);
        rvListaPlatos = findViewById(R.id.rv_menu_dishes_list);
        userLogged = UserLoggedContext.getInstance().getUser();
        loadDishesListToView();

    }

    private void loadDishesListToView () {
        List<MenuDish> listaPlatos = menuDishFacade.getMenuDishesByVendorID(userLogged.getId());
        menuAdapter = new MenuAdapter(this, listaPlatos);
        rvListaPlatos.setAdapter(menuAdapter);
        rvListaPlatos.setLayoutManager(new LinearLayoutManager(this));
    }
}