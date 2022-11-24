package co.edu.unipiloto.rapicoopproject.lib;

import android.location.Location;
import android.util.Log;

import java.util.Arrays;

public class Delivery {
    private int id;
    private String orderNumber;
    private String deliverId;
    private String destination;  //Only for card purposes
    private String origin;
    private float distance;
    private boolean ended;

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

    public String getOriginStringCoords() {
        return origin;
    }

    public double[] getOriginCoords() {
        return doubleArrFromStringCoords(origin);
    }

    public float getDistance() {
        String totalCoords = origin +"," + destination;
        double[] coords = doubleArrFromStringCoords(totalCoords);
        float[] results = new float[1];
        Location.distanceBetween(coords[0],coords[1],coords[2],coords[3],results);
        return results[0];
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDestinationStringCoords() { return destination; }

    public double[] getDestinationCoords() {
        return doubleArrFromStringCoords(destination);
    }

    public double[] doubleArrFromStringCoords(String coordsString) {
        String[] stringCoords = coordsString.split(",");
        double[] coordinates = new double[stringCoords.length];
        for (int i = 0; i<coordinates.length; i++){
            coordinates[i] = Double.parseDouble(stringCoords[i]);
        }
        return coordinates;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}

