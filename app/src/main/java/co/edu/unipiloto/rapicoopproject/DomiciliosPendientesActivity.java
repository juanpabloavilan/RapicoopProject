package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.rapicoopproject.lib.Order;
import co.edu.unipiloto.rapicoopproject.lib.User;
import co.edu.unipiloto.rapicoopproject.ui_components.OrderDeliveryCardAdapter;

public class DomiciliosPendientesActivity extends AppCompatActivity {
    RecyclerView rvListaOrdenesPendientes;
    User userLogged;
    OrderDeliveryCardAdapter orderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domicilios_pendientes);
        rvListaOrdenesPendientes = findViewById(R.id.rv_pending_order_list);
        loadOrdenesPendientes();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void loadOrdenesPendientes(){
        List<Order> listaOrdenes = new ArrayList<>();
        listaOrdenes.add(new Order("100001", "Cra 111c # 88-17", "Cl 7 #3-12", 4));
        listaOrdenes.add(new Order("100002", "Cra 1c # 10-17", "Cl 72 #3-12", 15));
        listaOrdenes.add(new Order("100003", "Cra 11c # 2-17", "Cl 7 #2-11", 22));
        listaOrdenes.add(new Order("100004", "Cra 11c # 99-7", "Cra 7 #3-12", 32));
        System.out.println("Size lista ordenes:   "+ listaOrdenes.size());
        orderAdapter = new OrderDeliveryCardAdapter(this, listaOrdenes);
        rvListaOrdenesPendientes.setAdapter(orderAdapter);
        rvListaOrdenesPendientes.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter.setOnItemClickListener(position -> {
            String orderNumberSelected = listaOrdenes.get(position).getOrderNumber();
            Intent intent = new Intent(DomiciliosPendientesActivity.this, CurrentDeliveryActivity.class);
            intent.putExtra(CurrentDeliveryActivity.ORDER_NUMBER_SELECTED, orderNumberSelected );
            startActivity(intent);
        });

    }
}