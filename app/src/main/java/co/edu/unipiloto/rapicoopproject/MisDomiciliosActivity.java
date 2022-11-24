package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.rapicoopproject.applicationcontext.CurrentLocationContext;
import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.entities.DeliveryFacade;
import co.edu.unipiloto.rapicoopproject.entities.OrderFacade;
import co.edu.unipiloto.rapicoopproject.entities.UserFacade;
import co.edu.unipiloto.rapicoopproject.lib.Delivery;
import co.edu.unipiloto.rapicoopproject.lib.Order;
import co.edu.unipiloto.rapicoopproject.lib.User;
import co.edu.unipiloto.rapicoopproject.ui_components.DeliveryCardAdapter;

public class MisDomiciliosActivity extends AppCompatActivity {
    RecyclerView rvListaOrdenesPendientes;
    User userLogged;
    OrderFacade orderFacade;
    DeliveryFacade deliveryFacade;
    UserFacade userFacade;
    DeliveryCardAdapter orderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domicilios_pendientes);
        rvListaOrdenesPendientes = findViewById(R.id.rv_pending_order_list);
        orderFacade = OrderFacade.getInstance(this);
        deliveryFacade = DeliveryFacade.getInstance(this);
        userFacade = UserFacade.getInstance(this);
        userLogged = UserLoggedContext.getInstance().getUser();
        loadDomicilios();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void loadDomicilios(){
        boolean hasActiveDelivery = deliveryFacade.hasActiveDelivery(userLogged.getId());
        List<Delivery> domicilios;
        if(hasActiveDelivery){
            domicilios = new ArrayList<>();
            domicilios.add(deliveryFacade.getDeliveryByDeliver(userLogged.getId()));
        } else {
            domicilios = getDomiciliosPendientes();
        }
        orderAdapter = new DeliveryCardAdapter(this, domicilios);
        rvListaOrdenesPendientes.setAdapter(orderAdapter);
        rvListaOrdenesPendientes.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter.setOnItemClickListener(position -> {
            String orderNumberSelected = domiciliosPendientes.get(position).getOrderNumber();
            String destination = domiciliosPendientes.get(position).getDestinationStringCoords();
            Intent intent = new Intent(DomiciliosPendientesActivity.this, CurrentDeliveryActivity.class);
            intent.putExtra(CurrentDeliveryActivity.ORDER_NUMBER_SELECTED, orderNumberSelected );
            intent.putExtra(CurrentDeliveryActivity.ORDER_DESTINATION, destination);
            startActivity(intent);
        });
    }

    public List<Delivery> getDomiciliosPendientes(){
        List<Order> unassignedOrders = orderFacade.getPendingOrders();
        ArrayList<Delivery> pendingDeliveries = new ArrayList<>();
        double[] coords = new double[] {
                CurrentLocationContext.getInstance().getLatitude(),
                CurrentLocationContext.getInstance().getLongitude()};//Get user location
        for (Order pendingOrder: unassignedOrders) {
            double[] targetCoords = userFacade
                    .getUserById(orderFacade.getOrderClientId(pendingOrder.getId()))
                    .getAddressCoordinates(this);
            Delivery mockDelivery = new Delivery(
                    String.valueOf(pendingOrder.getId()),
                    String.valueOf(userLogged.getId()),
                    ""+coords[0]+","+coords[1],""+targetCoords[0]+","+targetCoords[1]
            );
            pendingDeliveries.add(mockDelivery);
        }
        return pendingDeliveries;
    }
}