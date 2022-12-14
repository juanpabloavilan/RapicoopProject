package co.edu.unipiloto.rapicoopproject.ui_components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.edu.unipiloto.rapicoopproject.CurrentDeliveryActivity;
import co.edu.unipiloto.rapicoopproject.R;
import co.edu.unipiloto.rapicoopproject.lib.Delivery;

public class DeliveryCardAdapter extends RecyclerView.Adapter<DeliveryCardAdapter.DeliveryCardViewHolder>{
    private final Context context;
    private final List<Delivery> data;
    private OnItemClickListener mListener;

    public DeliveryCardAdapter(Context context, List<Delivery> data){
        this.context = context;
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void  setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public DeliveryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_card, parent, false);
        return new DeliveryCardViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryCardViewHolder holder, int position) {

        holder.orderNumber.setText(data.get(position).getOrderNumber());
        holder.destination.setText(data.get(position).getDestinationAddress(context));
        holder.origin.setText(data.get(position).getOriginAddress(context));
        holder.distance.setText(String.format("%.2f", data.get(position).getDistance()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class DeliveryCardViewHolder extends RecyclerView.ViewHolder{
        private final TextView orderNumber, origin, destination, distance;
        public DeliveryCardViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            orderNumber=itemView.findViewById(R.id.numero_orden);
            origin=itemView.findViewById(R.id.origen);
            destination=itemView.findViewById(R.id.destino);
            distance= itemView.findViewById(R.id.distance);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (listener!= null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
