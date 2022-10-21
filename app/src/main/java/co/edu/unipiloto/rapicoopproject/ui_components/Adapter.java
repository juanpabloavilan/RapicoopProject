package co.edu.unipiloto.rapicoopproject.ui_components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.edu.unipiloto.rapicoopproject.R;

public class Adapter extends RecyclerView.Adapter<Adapter.CardViewHolder> {
    private Context context;
    private List<MenuDishCard> data;

    public Adapter(Context context, List<MenuDishCard> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dish_menu_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.ivImageFood.setImageResource(data.get(position).getPicture());
        holder.tvPrecio.setText(data.get(position).getPrecio());
        holder.tvNombrePlato.setText(data.get(position).getNombre());
        holder.tvDescripcion.setText(data.get(position).getDescripcion());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivImageFood;
        private TextView tvNombrePlato, tvDescripcion, tvPrecio;

        public CardViewHolder(View view){
            super(view);
            ivImageFood = view.findViewById(R.id.image_food);
            tvNombrePlato = view.findViewById(R.id.nombre_plato);
            tvDescripcion = view.findViewById(R.id.descripcion);
            tvPrecio = view.findViewById(R.id.precio);

        }
    }
}
