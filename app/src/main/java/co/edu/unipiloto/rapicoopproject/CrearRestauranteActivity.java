package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.entities.RestaurantFacade;
import co.edu.unipiloto.rapicoopproject.lib.Restaurant;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class CrearRestauranteActivity extends AppCompatActivity {

    private User userLogged;
    private RestaurantFacade restaurantFacade;
    private EditText name, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_restaurante);
        userLogged = UserLoggedContext.getInstance().getUser();
        restaurantFacade = RestaurantFacade.getInstance(this);
        Button crearRestaurante = findViewById(R.id.crear_restaurante_btn);
        Button cancelar = findViewById(R.id.cancelar_btn);
        name = findViewById(R.id.name_et);
        type = findViewById(R.id.type_et);
        crearRestaurante.setOnClickListener(this::insertarRestaurante);
        cancelar.setOnClickListener(this::quit);
    }

    private void insertarRestaurante(View view){
        Restaurant newRestaurant = new Restaurant(name.getText().toString(),type.getText().toString(),userLogged.getId());
        String insertionResult = restaurantFacade.insertRestaurant(newRestaurant) != -1 ?
                "Se creo el restaurante exitosamente" : "Error al crear el restaurante";
        Toast.makeText(this,insertionResult,Toast.LENGTH_LONG).show();
    }

    private void quit(View view){ finish(); }
}