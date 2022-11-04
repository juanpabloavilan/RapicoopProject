package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.entities.RestaurantFacade;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class MiRestauranteActivity extends AppCompatActivity {

    private RestaurantFacade restaurantFacade;
    private User userLogged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_restaurante);

        userLogged = UserLoggedContext.getInstance().getUser();
        TextView newRestaurantLink = findViewById(R.id.crear_restaurante_tv);
        Button alquilarCocinaBtn = findViewById(R.id.alquilar_cocina_btn);
        newRestaurantLink.setOnClickListener(this::onClickCreateRestaurant);
        alquilarCocinaBtn.setOnClickListener(this::onClickViewKitchens);
        restaurantFacade = RestaurantFacade.getInstance(this);
    }

    private void onClickCreateRestaurant(View view){
        if (activeRestaurant()){
            Toast.makeText(this,"Ya tienes un restaurante!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent crearRestaurante = new Intent(MiRestauranteActivity.this, CrearRestauranteActivity.class);
        startActivity(crearRestaurante);
    }

    private void onClickViewKitchens(View view){
        if (!activeRestaurant()){
            Toast.makeText(this,"No puedes acceder aqui sin crear un restaurante",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent verCocinasDisponibles = new Intent(MiRestauranteActivity.this, MiCocinaActivity.class);
        startActivity(verCocinasDisponibles);
    }

    private boolean activeRestaurant(){
        return restaurantFacade.hasRestaurant(userLogged.getId());
    }
}