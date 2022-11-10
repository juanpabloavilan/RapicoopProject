package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import co.edu.unipiloto.rapicoopproject.entities.RestaurantFacade;
import co.edu.unipiloto.rapicoopproject.lib.Restaurant;
import co.edu.unipiloto.rapicoopproject.ui_components.RestaurantsAdapter;

public class RestaurantsActivity extends AppCompatActivity {
    RestaurantFacade restaurantFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        RecyclerView restaurantsRv = findViewById(R.id.restaurantRecyclerView);
        RecyclerView recommendedRv = findViewById(R.id.recommendedRecyclerView);

        restaurantFacade = RestaurantFacade.getInstance(this);
        List<Restaurant> restaurantList = restaurantFacade.getRestaurants();

        RestaurantsAdapter adapter = new RestaurantsAdapter(this,restaurantList);
        restaurantsRv.setAdapter(adapter);
        restaurantsRv.setLayoutManager(new LinearLayoutManager(this));
        recommendedRv.setAdapter(adapter);
        recommendedRv.setLayoutManager(new LinearLayoutManager(this));
    }
}