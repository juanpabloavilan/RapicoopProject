package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.rapicoopproject.ui_components.RestaurantCard;
import co.edu.unipiloto.rapicoopproject.ui_components.RestaurantsAdapter;

public class RestaurantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        RecyclerView restaurantsRv = findViewById(R.id.restaurantRecyclerView);
        RecyclerView recommendedRv = findViewById(R.id.recommendedRecyclerView);

        List<RestaurantCard> restaurantList = new ArrayList<>();
        /*
        restaurantList.add(new RestaurantCard("El corral", "Chapinero", R.drawable.corral_logo, R.drawable.corral_background));
        restaurantList.add(new RestaurantCard("El corral", "Chapinero", R.drawable.corral_logo, R.drawable.corral_background));
        restaurantList.add(new RestaurantCard("El corral", "Chapinero", R.drawable.corral_logo, R.drawable.corral_background));
        restaurantList.add(new RestaurantCard("El corral", "Chapinero", R.drawable.corral_logo, R.drawable.corral_background));

        RestaurantsAdapter adapter = new RestaurantsAdapter(this,restaurantList);
        restaurantsRv.setAdapter(adapter);
        restaurantsRv.setLayoutManager(new LinearLayoutManager(this));
        recommendedRv.setAdapter(adapter);
        recommendedRv.setLayoutManager(new LinearLayoutManager(this));
        */

    }
}