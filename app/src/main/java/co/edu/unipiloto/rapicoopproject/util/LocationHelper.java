package co.edu.unipiloto.rapicoopproject.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.util.List;

public class LocationHelper {

    public LocationHelper(){}

    public double[] doubleArrFromStringCoords(String coordsString) {
        String[] stringCoords = coordsString.split(",");
        double[] coordinates = new double[stringCoords.length];
        for (int i = 0; i<coordinates.length; i++){
            coordinates[i] = Double.parseDouble(stringCoords[i]);
        }
        return coordinates;
    }

    public float getDistanceBetweenCoords(String origin, String destination) {
        String totalCoords = origin +"," + destination;
        double[] coords = doubleArrFromStringCoords(totalCoords);
        float[] results = new float[1];
        Location.distanceBetween(coords[0],coords[1],coords[2],coords[3],results); //distance is givin in meters
        return results[0]/1000; //converting to Kms
    }

    public String getAddressFromCoords(double[] coords, Context context){
        Geocoder geocoder = new Geocoder(context);
        try{
            List<Address> addresses = geocoder.getFromLocation(
                    coords[0], coords[1], 1
            );
            return addresses.get(0).getAddressLine(0);
        } catch(Exception err) {
            err.printStackTrace();
        }
        return "";
    }
}
