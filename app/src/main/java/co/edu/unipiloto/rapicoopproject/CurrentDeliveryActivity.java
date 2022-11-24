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
    private FusedLocationProviderClient fusedLocationProviderClient;
    DeliveryFacade deliveryFacade;

    private OdometerService odometro;
    private boolean enlazado = false;
    private double[] deliverLocation = null;

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

        orderNumber = getIntent().getStringExtra(ORDER_NUMBER_SELECTED);

        //Assigning layout components
        btnStartDelivery = findViewById(R.id.btn_start_delivery);
        btnPickUpDelivery = findViewById(R.id.btn_pickup_delivery);
        btnDeliverOrder = findViewById(R.id.btn_pickup_delivery);

        tvLatitud = findViewById(R.id.latitud);
        tvLongitud = findViewById(R.id.longitud);
        tvCountry = findViewById(R.id.country);
        tvLocalidad = findViewById(R.id.localidad);
        tvDireccion = findViewById(R.id.direccion);
        tvOrderNumberSelected = findViewById(R.id.order_selected);
        userLoggedIn = UserLoggedContext.getInstance().getUser();

        tvOrderNumberSelected.setText(String.format("Order number %s", orderNumber));

        //Facade Domicilios
        deliveryFacade = DeliveryFacade.getInstance(this);
        //Obteniendo location Services
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Agregando listeners a los botones
        //****Al aceptar solicitud de pedido
        btnStartDelivery.setOnClickListener((v) -> {
            //Obtener la ubicación actual
            getCurrentLocationProcess(); //Este metodo verifica los permisos de ubicación y llama al método getLocation
            //Crear entidad domicilio (Mateo)
            double[] deliverLocation = getOrderTargetLocation();
            //Delivery confirmation = new Delivery(orderNumber,
            //        String.valueOf(userLoggedIn.getId()), //Delivery guy id
            //        deliverLocation[0] + "," + deliverLocation[1]
            //);

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



    /**
     * Obtiene la ubicación actual y la despliega en la interfaz
     */
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e(TAG, "Location Permissions have been declined");
            ActivityCompat.requestPermissions(CurrentDeliveryActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    44);
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(
                new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location != null) {
                            deliverLocation = new double[] { location.getLatitude(), location.getLongitude() };
                            Geocoder geocoder = new Geocoder(CurrentDeliveryActivity.this,
                                    Locale.getDefault());

                            try {
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1
                                );

                                tvLatitud.setText(String.format("Latitud %s", addresses.get(0).getLatitude()));
                                tvLongitud.setText(String.format("Longitud %s", addresses.get(0).getLongitude()));
                                tvCountry.setText(addresses.get(0).getCountryName());
                                tvLocalidad.setText(addresses.get(0).getLocality());
                                tvDireccion.setText(addresses.get(0).getAddressLine(0));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
    }
    private void getCurrentLocationProcess(){
        Log.e(TAG, "Checking location services permission");
        if (ActivityCompat.checkSelfPermission(CurrentDeliveryActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Location Permissions have been already granted");
            getLocation();
        } else {
            Log.e(TAG, "Location Permissions have been declined");
            ActivityCompat.requestPermissions(CurrentDeliveryActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    44);
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

    public double[] getOrderTargetLocation(){
        //TODO: return String location from order Facade;
        double latitude = Double.parseDouble("4.746336");
        double longitude = Double.parseDouble("-74.043067");
        return new double[] { latitude,longitude };
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