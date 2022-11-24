package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.entities.MenuDishFacade;
import co.edu.unipiloto.rapicoopproject.entities.RestaurantFacade;
import co.edu.unipiloto.rapicoopproject.lib.MenuDish;
import co.edu.unipiloto.rapicoopproject.lib.User;
import co.edu.unipiloto.rapicoopproject.ui_components.MenuAdapter;

public class MiMenuActivity extends AppCompatActivity {
    MenuDishFacade menuDishFacade;
    RecyclerView rvListaPlatos;
    MenuAdapter menuAdapter;
    User userLogged;
    Button btnGoToAddMenuDish;
    RestaurantFacade restaurantFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_menu);
        btnGoToAddMenuDish = findViewById(R.id.btn_go_to_add_menu_dish);
        rvListaPlatos = findViewById(R.id.rv_menu_dishes_list);
        restaurantFacade = RestaurantFacade.getInstance(this);

        menuDishFacade = MenuDishFacade.getInstance(this);
        userLogged = UserLoggedContext.getInstance().getUser();
        loadDishesListToView();

        btnGoToAddMenuDish.setOnClickListener(this::goToAddMenuDish);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void loadDishesListToView () {
        int restaurantId = restaurantFacade.getRestaurantIdByUserId(userLogged.getId());
        List<MenuDish> listaPlatos = menuDishFacade.getMenuDishesByVendorID(restaurantId);
        if(listaPlatos == null) return;
        if(listaPlatos.size() == 0) return;
        menuAdapter = new MenuAdapter(this, listaPlatos);
        rvListaPlatos.setAdapter(menuAdapter);
        rvListaPlatos.setLayoutManager(new LinearLayoutManager(this));
    }

    private void goToAddMenuDish(View view){
        Intent goToAddMenuDishActivity = new Intent(this, AddMenuDishActivity.class);
        startActivity(goToAddMenuDishActivity);
    }
}