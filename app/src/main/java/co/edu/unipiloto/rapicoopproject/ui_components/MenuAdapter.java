package co.edu.unipiloto.rapicoopproject.ui_components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import co.edu.unipiloto.rapicoopproject.R;
import co.edu.unipiloto.rapicoopproject.lib.MenuDish;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.CardViewHolder> {
    private final Context context;
    private final List<MenuDish> data;
    private OnItemClickListener mListener;

    public MenuAdapter(Context context, List<MenuDish> data) {
        this.context = context;
        this.data = data;
    }
    public interface OnItemClickListener {
        void onItemClickDecrease(int position);
        void onItemClickIncrease(int position);
        int getCurrentCantidad(int position);
    }
    public void  setOnItemClickListener(OnItemClickListener listener){
        mListener =  listener;
    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dish_menu_card, parent, false);
        return new CardViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        String nombre = data.get(position).getNombre();
        String descripcion = data.get(position).getDescripcion();
        Integer precioRaw = data.get(position).getPrecio();
        String precioFormattedAsCurrency = NumberFormat.getCurrencyInstance(new Locale("es", "CO")).format(precioRaw);
        holder.ivImageFood.setImageResource(R.drawable.hot_dog);
        holder.tvPrecio.setText(precioFormattedAsCurrency);
        holder.tvNombrePlato.setText(nombre);
        holder.tvDescripcion.setText(descripcion);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{

        private final ImageView ivImageFood;
        private final TextView tvNombrePlato, tvDescripcion, tvPrecio;
        private TextView tvCantidad;
        private Button btnIncrease, btnDecrease;

        public CardViewHolder(@NonNull View view, OnItemClickListener listener){
            super(view);
            ivImageFood = view.findViewById(R.id.image_food);
            tvNombrePlato = view.findViewById(R.id.nombre_plato);
            tvDescripcion = view.findViewById(R.id.origen);
            tvPrecio = view.findViewById(R.id.precio);
            tvCantidad = view.findViewById(R.id.cantidad);
            btnDecrease = view.findViewById(R.id.btn_decrease);
            btnIncrease = view.findViewById(R.id.btn_increase);
            if (listener!= null){
                int position =getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    int cantidad =listener.getCurrentCantidad(position);
                    tvCantidad.setText(String.format("%d", cantidad));
                }
            }


            btnIncrease.setOnClickListener((v)-> {
                if (listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onItemClickIncrease(position);
                        int cantidad =listener.getCurrentCantidad(position);
                        tvCantidad.setText(String.format("%d", cantidad));
                    }
                }
            });
            btnDecrease.setOnClickListener((v)-> {
                if (listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onItemClickDecrease(position);
                        int cantidad =listener.getCurrentCantidad(position);
                        tvCantidad.setText(String.format("%d", cantidad));
                    }
                }
            });


        }
    }
}
