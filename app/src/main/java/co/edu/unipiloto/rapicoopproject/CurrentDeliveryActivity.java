package co.edu.unipiloto.rapicoopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentDeliveryActivity extends AppCompatActivity {
    public static final String ORDER_NUMBER_SELECTED = "ORDER_NUMBER_SELECTED";
    private final String TAG = "CURRENT_DELIVERY_ACTIVITY";
    private Button btnLocate;
    private TextView tvLatitud;
    private TextView tvLongitud;
    private TextView tvCountry;
    private TextView tvLocalidad;
    private TextView tvDireccion;
    private TextView tvOrderNumberSelected;
    private String orderNumber;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_delivery);

        orderNumber = getIntent().getStringExtra(ORDER_NUMBER_SELECTED);
        //Assigning layout components

        btnLocate = findViewById(R.id.btn_current_location);
        tvLatitud = findViewById(R.id.latitud);
        tvLongitud = findViewById(R.id.longitud);
        tvCountry = findViewById(R.id.country);
        tvLocalidad = findViewById(R.id.localidad);
        tvDireccion = findViewById(R.id.direccion);
        tvOrderNumberSelected = findViewById(R.id.order_selected);

        tvOrderNumberSelected.setText(String.format("Order number %s", orderNumber));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        btnLocate.setOnClickListener((v) -> {
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
        });

    }

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
}