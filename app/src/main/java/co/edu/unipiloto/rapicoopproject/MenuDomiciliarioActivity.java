package co.edu.unipiloto.rapicoopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import co.edu.unipiloto.rapicoopproject.entities.UserFacade;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class MenuDomiciliarioActivity extends AppCompatActivity {
    private static final String TAG = "MENU_DOMICILIARIO_ACTIVITY";

    TextView welcomeMessageTextView;
    Button misPedidosPendientesButton;
    Button misPedidosRealizadosButton;
    Button editarMiPerfilButton;
    Button miRutaActivaBtn;
    User userLogged;
    OrderFacade orderFacade;
    DeliveryFacade deliveryFacade;
    UserFacade userFacade;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private double[] deliverLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_domiciliario);

        userLogged = UserLoggedContext.getInstance().getUser();
        orderFacade = OrderFacade.getInstance(this);
        deliveryFacade = DeliveryFacade.getInstance(this);
        userFacade = UserFacade.getInstance(this);
        welcomeMessageTextView = findViewById(R.id.user_logged_welcome_message);

        //Actualizar View con los datos del usuario loggeado
        welcomeMessageTextView.setText(welcomeMessageTextView.getText().toString() + ", " + userLogged.getFullName());

        misPedidosPendientesButton = findViewById(R.id.mis_pedidos_pendientes_btn);
        misPedidosRealizadosButton = findViewById(R.id.mis_pedidos_entregados_btn);
        editarMiPerfilButton = findViewById(R.id.editar_perfil_btn);
        miRutaActivaBtn = findViewById(R.id.ver_ruta_btn);

        //Añadiendo Click event Handlers
        misPedidosPendientesButton.setOnClickListener(this::onClickGoToMisPedidosPendientes);
        misPedidosRealizadosButton.setOnClickListener(this::onClickGoToMisPedidosRealizados);
        editarMiPerfilButton.setOnClickListener(this::onClickGoToEditarPerfil);
        miRutaActivaBtn.setOnClickListener(this::onClickViewActiveRoute);

        //Obteniendo location Services
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Obtener la ubicación actual
        getCurrentLocationProcess(); //Este metodo verifica los permisos de ubicación y llama al método getLocation
    }

    private void onClickGoToEditarPerfil(View view) {
        Intent intent = new Intent(MenuDomiciliarioActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }

    private void onClickGoToMisPedidosRealizados(View view) {

    }

    private void onClickGoToMisPedidosPendientes(View view){
        Intent intent = new Intent(MenuDomiciliarioActivity.this, MisDomiciliosActivity.class);
        startActivity(intent);
    }

    public void onClickViewActiveRoute(View view) {
        double[] targetCoords = getOrderRoute();
        Uri.Builder builder= new Uri.Builder();
        builder.scheme("https").authority("www.google.com").
                appendPath("maps").
                appendPath("dir").
                appendPath("").
                appendQueryParameter("api", "1").
                appendQueryParameter("destination", targetCoords[0] + "," + targetCoords[1]).
                appendQueryParameter("travelmode","driving");
        String url= builder.build().toString();
        Intent intent= new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private double[] getOrderRoute(){
        int orderId = deliveryFacade.getOrderIdByDeliver(userLogged.getId());
        int clientId = orderFacade.getOrderClientId(orderId);
        return userFacade.getUserCoordinates(clientId,this);
    }

    private void getCurrentLocationProcess(){
        Log.e(TAG, "Checking location services permission");
        if (ActivityCompat.checkSelfPermission(MenuDomiciliarioActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Location Permissions have been already granted");
            getLocation();
        } else {
            Log.e(TAG, "Location Permissions have been declined");
            ActivityCompat.requestPermissions(MenuDomiciliarioActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    44);
        }
    }

    /**
     * Obtiene la ubicación actual y la guarda en un contexto
     */
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.e(TAG, "Location Permissions have been declined");
            ActivityCompat.requestPermissions(MenuDomiciliarioActivity.this,
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
                            Geocoder geocoder = new Geocoder(MenuDomiciliarioActivity.this,
                                    Locale.getDefault());

                            try {
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1
                                );
                                CurrentLocationContext.getInstance().setAddresses(addresses);
                                double latitude = CurrentLocationContext.getInstance().getLatitude();
                                double longitude = CurrentLocationContext.getInstance().getLongitude();
                                Toast.makeText(MenuDomiciliarioActivity.this, "Ubicacion actual latitud= " +latitude+" longitud= "+longitude , Toast.LENGTH_SHORT).show();


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
    }
}