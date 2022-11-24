package co.edu.unipiloto.rapicoopproject.lib;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.util.Arrays;

import co.edu.unipiloto.rapicoopproject.util.LocationHelper;
import kotlin.text.UStringsKt;

public class Delivery {
    private int id;
    private String orderNumber;
    private String deliverId;
    private String destination;  //Only for card purposes
    private String origin;
    private float distance;
    private boolean ended;
    LocationHelper locationHelper = new LocationHelper();

    public Delivery(String orderNumber, String deliverId, String source, String destination) {
        this.orderNumber = orderNumber;
        this.deliverId = deliverId;
        this.origin = source;
        this.destination = destination;
        this.distance = getDistance();
        this.ended = false;
    }

    public Delivery(int id, String orderNumber, String deliverId,  String origin, String destination, boolean ended) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.deliverId = deliverId;
        this.destination = destination;
        this.origin = origin;
        this.ended = ended;
        this.distance = getDistance();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public String getOriginAddress() {
        return origin;
    }

    public double[] getOriginCoords() {
        return locationHelper.doubleArrFromStringCoords(origin);
    }

    public float getDistance() {
        return locationHelper.getDistanceBetweenCoords(origin,destination);
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getOriginStringCoords() { return origin; }

    public String getDestinationStringCoords() { return destination; }

    public double[] getDestinationCoords() {
        return locationHelper.doubleArrFromStringCoords(destination);
    }

    public String getOriginAddress(Context context){
        return locationHelper.getAddressFromCoords(getOriginCoords(),context);
    }

    public String getDestinationAddress(Context context){
        return locationHelper.getAddressFromCoords(getDestinationCoords(),context);
    }



    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getId() {
        return id;
    }
}

