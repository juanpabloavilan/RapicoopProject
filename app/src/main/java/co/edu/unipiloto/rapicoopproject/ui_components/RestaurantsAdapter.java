package co.edu.unipiloto.rapicoopproject.ui_components;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.edu.unipiloto.rapicoopproject.ClientRestaurantMenu;
import co.edu.unipiloto.rapicoopproject.R;
import co.edu.unipiloto.rapicoopproject.lib.Restaurant;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.restaurantViewHolder> {

    Context context;
    List<Restaurant> restaurantsData;

    public RestaurantsAdapter(Context context, List<Restaurant> data) {
        this.context = context;
        this.restaurantsData = data;
    }


    @NonNull
    @Override
    public restaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.restaurant_card,parent,false);
        return new restaurantViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull restaurantViewHolder holder, int position) {
        holder.background_img.setImageResource(restaurantsData.get(position).getBackgroundImg());
        holder.logo_img.setImageResource(restaurantsData.get(position).getLogoImg());
        holder.tv_name.setText(restaurantsData.get(position).getName());
        holder.go_btn.setOnClickListener(v -> {
                Intent restaurantMenu = new Intent(context, ClientRestaurantMenu.class);
                restaurantMenu.putExtra(ClientRestaurantMenu.ID_RESTAURANTE, restaurantsData.get(position).getId());
                context.startActivity(restaurantMenu);
        });
        holder.background_img.setOnClickListener(v -> {
            Intent restaurantMenu = new Intent(context, ClientRestaurantMenu.class);
            restaurantMenu.putExtra(ClientRestaurantMenu.ID_RESTAURANTE, restaurantsData.get(position).getId());
            context.startActivity(restaurantMenu);
        });
    }

    @Override
    public int getItemCount() {
        return restaurantsData.size();
    }

    public static class restaurantViewHolder extends RecyclerView.ViewHolder {

        ImageView background_img, logo_img;
        TextView tv_name;
        Button go_btn;

        public restaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            background_img = itemView.findViewById(R.id.restaurant_background);
            logo_img = itemView.findViewById(R.id.restaurant_logo);
            tv_name = itemView.findViewById(R.id.restaurant_name);
            go_btn = itemView.findViewById(R.id.menu_btn);
        }
    }
}
