package co.edu.unipiloto.rapicoopproject.applicationcontext;

import android.location.Address;

import java.util.List;



public class CurrentLocationContext {
    private static CurrentLocationContext instance;
    private List<Address> addresses;
    private double latitude;
    private double longitude;
    private String country;
    private String locality;
    private String address;

    private void CurrentDeliveryActivity(){
    }

    public static synchronized CurrentLocationContext getInstance(){
        if(instance == null){
            instance = new CurrentLocationContext();
        }
        return instance;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public double getLatitude() {
        return addresses.get(0).getLatitude();
    }

    public double getLongitude() {
        return addresses.get(0).getLongitude();
    }

    public String getCountry() {
        return addresses.get(0).getCountryName();
    }

    public String getLocality() {
        return addresses.get(0).getLocality();
    }

    public String getAddress() {
        return addresses.get(0).getAddressLine(0);
    }
}
