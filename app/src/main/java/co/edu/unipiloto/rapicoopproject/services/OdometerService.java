package co.edu.unipiloto.rapicoopproject.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class OdometerService extends Service {
    public static String TAG = "ODOMETER_SERVICE";
    //A traves del binder podemos tener acceso al getOdometer
    private final IBinder binder = new OdometerBinder();


    //Servicios de localización.
    private LocationListener listener;
    //Gestión de la localización
    private LocationManager locationManager;
    private static double distancia=0;
    private static Location lastLocation = null;
    //Declarando clase OdometerBinder que implementa la interfaz de conexión a traves de un metodo que puede
    //ser llamado por los clientes del servisio
    public class OdometerBinder extends Binder {
        public OdometerService getOdometer() {
            return OdometerService.this;
        }
    }

    public OdometerService() {
    }

    //Incluyendo listener de location en el servicio enlazado
    @Override
    public void onCreate() {
        super.onCreate();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                //Manejar el cambio de la ubicación
                if (lastLocation == null){
                    lastLocation=location;
                }
                distancia += location.distanceTo(lastLocation);
                lastLocation = location;
            }

            @Override
            public void onProviderDisabled(String arg0) {

            }

            @Override
            public void onProviderEnabled(String arg0) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle bundle) {

            }
        };
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String proveedor = locationManager.getBestProvider(new Criteria(), true);
        if (proveedor != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(proveedor, 3000, 2, listener);
        }
    }


    //onDestroy deshabilita el listener y la gestion del location
    @Override
    public void onDestroy(){
        super.onDestroy();
        if (locationManager!=null && listener!=null){
            locationManager.removeUpdates(listener);
        }
        locationManager=null;
        listener=null;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    //Aqui se ponen los métodos funcionales.
    public double getDistance(){
        Log.v(TAG, "Refreshing distance= "+distancia);
        return distancia;
    }


}