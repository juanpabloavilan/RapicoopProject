package co.edu.unipiloto.rapicoopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import co.edu.unipiloto.rapicoopproject.applicationcontext.CurrentLocationContext;
import co.edu.unipiloto.rapicoopproject.applicationcontext.UserLoggedContext;
import co.edu.unipiloto.rapicoopproject.entities.DeliveryFacade;
import co.edu.unipiloto.rapicoopproject.entities.OrderFacade;
import co.edu.unipiloto.rapicoopproject.enums.OrderStatus;
import co.edu.unipiloto.rapicoopproject.lib.Delivery;
import co.edu.unipiloto.rapicoopproject.lib.User;
import co.edu.unipiloto.rapicoopproject.services.DeliveryNotificationService;
import co.edu.unipiloto.rapicoopproject.services.OdometerService;


public class CurrentDeliveryActivity extends AppCompatActivity {
    public static final String ORDER_NUMBER_SELECTED = "ORDER_NUMBER_SELECTED";
    public static final String ORDER_DESTINATION = "ORDER_DESTINATION";
    private final String TAG = "CURRENT_DELIVERY_ACTIVITY";

    private OrderFacade orderFacade;
    private User userLoggedIn;
    public static final String DELIVERY_NOTIFICATIONS_CHANNEL = "deliveryChannel";

    private Button btnStartDelivery;
    private Button btnPickUpDelivery;
    private Button btnDeliverOrder;

    private TextView tvLatitud;
    private TextView tvLongitud;
    private TextView tvCountry;
    private TextView tvLocalidad;
    private TextView tvDireccion;
    private TextView tvOrderNumberSelected;
    private TextView tvDistanciaRecorrida;
    private String orderNumber;

    DeliveryFacade deliveryFacade;

    private OdometerService odometro;
    private boolean enlazado = false;

    private CurrentLocationContext locationContext;


    //Declarando objeto conexión al Servicio odometro
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //Conectar el servicio
            OdometerService.OdometerBinder odometerBinder = (OdometerService.OdometerBinder) iBinder;
            odometro = odometerBinder.getOdometer();
            enlazado = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //Desconectar el servicio
            enlazado=false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_delivery);
        createNotificationsChannel();
        userLoggedIn = UserLoggedContext.getInstance().getUser();
        locationContext = CurrentLocationContext.getInstance();

        orderNumber = getIntent().getStringExtra(ORDER_NUMBER_SELECTED);

        //Assigning layout components
        btnStartDelivery = findViewById(R.id.btn_start_delivery);
        btnPickUpDelivery = findViewById(R.id.btn_pickup_delivery);
        btnDeliverOrder = findViewById(R.id.btn_deliver_food);

        tvLatitud = findViewById(R.id.latitud);
        tvLatitud.setText(String.format("Latitud: %s", locationContext.getLatitude()));
        tvLongitud = findViewById(R.id.longitud);
        tvLongitud.setText(String.format("Longitud: %s", locationContext.getLongitude()));
        tvCountry = findViewById(R.id.country);
        tvCountry.setText(String.format("Pais: %s", locationContext.getCountry()));
        tvLocalidad = findViewById(R.id.localidad);
        tvLocalidad.setText(String.format("Localidad: %s", locationContext.getLocality()));
        tvDireccion = findViewById(R.id.direccion);
        tvDireccion.setText(String.format("Direccion: %s", locationContext.getAddress()));
        tvOrderNumberSelected = findViewById(R.id.order_selected);


        tvOrderNumberSelected.setText(String.format("Order number %s", orderNumber));

        //Facade Domicilios
        deliveryFacade = DeliveryFacade.getInstance(this);


        //Agregando listeners a los botones
        //****Al aceptar solicitud de pedido
        btnStartDelivery.setOnClickListener((v) -> {

            //Crear entidad domicilio (Mateo)
            String deliverCoords = CurrentLocationContext.getInstance().getLatitude() + "," +
                    CurrentLocationContext.getInstance().getLongitude();
            String destinationCoords = getIntent().getStringExtra(ORDER_DESTINATION);
            Delivery confirmation = new Delivery(orderNumber,
                    String.valueOf(userLoggedIn.getId()), //Delivery guy id
                    deliverCoords,
                    destinationCoords
            );
            deliveryFacade.insertDelivery(confirmation);
            //UPDATE ORDER STATUS
            //Notificar cliente y restaurante (STARTED SERVICE)
            notify(OrderStatus.ACEPTADA);
            //Llamar al bound service odometer para empezar a registrar la distancia recorrida por parte del domiciliario
            showDistance();
            //Hacer visible el boton de Recoger pedido en el restaurante.
            btnStartDelivery.setVisibility(View.GONE);
            findViewById(R.id.info_aceptar_pedido).setVisibility(View.GONE);
            btnPickUpDelivery.setVisibility(View.VISIBLE);
            findViewById(R.id.info_recoger_pedido).setVisibility(View.VISIBLE);
        });

        btnPickUpDelivery.setOnClickListener((v)->{
            btnPickUpDelivery.setVisibility(View.GONE);
            findViewById(R.id.info_recoger_pedido).setVisibility(View.GONE);
            btnDeliverOrder.setVisibility(View.VISIBLE);
            findViewById(R.id.info_entregar_pedido).setVisibility(View.VISIBLE);
            notify(OrderStatus.EN_CAMINO);
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(CurrentDeliveryActivity.this, OdometerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        //startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (enlazado){
            unbindService(connection);
            enlazado = false;
        }
    }



    public void showDistance() {
        tvDistanciaRecorrida = findViewById(R.id.distancia_recorrida);
        final Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                double distancia = 0;
                if (enlazado && odometro != null) {
                    distancia = odometro.getDistance();
                }
                String sDistancia = String.format(Locale.getDefault(), "%1$,.2f kilometros", distancia);
                tvDistanciaRecorrida.setText(String.format("Distancia recorrida: %s", sDistancia));

                handler.postDelayed(this, 1000);
            }
        });
    }

    public void notify(OrderStatus status){
        Intent notification = new Intent(CurrentDeliveryActivity.this, DeliveryNotificationService.class);
        switch(status){
            case ACEPTADA:
                notification.putExtra(DeliveryNotificationService.EXTRA_STATUS, userLoggedIn.getFullName() + ", ha aceptado el pedido, acerquese al restaurante");
                break;
            case EN_CAMINO:
                notification.putExtra(DeliveryNotificationService.EXTRA_STATUS, "Dirijase a la direccion indicada para realizar la entrega");
        }
        startService(notification);
    }

    private void createNotificationsChannel() {

    }
}