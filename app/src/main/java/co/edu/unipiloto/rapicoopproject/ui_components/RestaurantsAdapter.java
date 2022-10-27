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
import co.edu.unipiloto.rapicoopproject.RestaurantsActivity;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.restaurantViewHolder> {

    Context context;
    List<RestaurantCard> restaurantsData;

    public RestaurantsAdapter(Context context, List<RestaurantCard> data) {
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
        holder.background_img.setImageResource(restaurantsData.get(position).getBackground());
        holder.logo_img.setImageResource(restaurantsData.get(position).getRestaurantLogo());
        holder.tv_name.setText(restaurantsData.get(position).getRestaurantName());
        holder.tv_locality.setText(restaurantsData.get(position).getRestaurantLocality());
        holder.go_btn.setOnClickListener(v -> {
                Intent restaurantMenu = new Intent(context, ClientRestaurantMenu.class);
                context.startActivity(restaurantMenu);
        });
        holder.background_img.setOnClickListener(v -> {
            Intent restaurantMenu = new Intent(context, ClientRestaurantMenu.class);
            context.startActivity(restaurantMenu);
        });
    }

    @Override
    public int getItemCount() {
        return restaurantsData.size();
    }

    public static class restaurantViewHolder extends RecyclerView.ViewHolder {

        ImageView background_img, logo_img;
        TextView tv_name, tv_locality;
        Button go_btn;

        public restaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            background_img = itemView.findViewById(R.id.restaurant_background);
            logo_img = itemView.findViewById(R.id.restaurant_logo);
            tv_name = itemView.findViewById(R.id.restaurant_name);
            tv_locality = itemView.findViewById(R.id.restaurant_locality);
            go_btn = itemView.findViewById(R.id.menu_btn);
        }
    }
}
