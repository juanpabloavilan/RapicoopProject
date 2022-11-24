package co.edu.unipiloto.rapicoopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.entities.DeliveryFacade;
import co.edu.unipiloto.rapicoopproject.lib.Delivery;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class HistorialDomiciliosActivity extends AppCompatActivity {

    ListView domiciliosListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_domicilios);
        domiciliosListView = findViewById(R.id.historialDomicilios);
        DeliveryFacade deliveryFacade = DeliveryFacade.getInstance(this);
        User userLogged = UserLoggedContext.getInstance().getUser();
        ArrayList<Delivery> deliveries = deliveryFacade.getUserDeliveries(userLogged.getId());
        ArrayAdapter<Delivery> adapter = new ArrayAdapter<Delivery>(this, android.R.layout.simple_list_item_1, deliveries);
        domiciliosListView.setAdapter(adapter);
    }
}