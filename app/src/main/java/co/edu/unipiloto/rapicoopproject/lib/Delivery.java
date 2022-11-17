package co.edu.unipiloto.rapicoopproject.lib;

public class Delivery {
    private int id;
    private String orderNumber;
    private String deliverId;
    private String destination;  //Only for card purposes
    private String origin;
    private int distance;
    private boolean ended;

    public Delivery(String orderNumber, String deliverId, String source) {
        this.orderNumber = orderNumber;
        this.deliverId = deliverId;
        this.origin = source;
        this.ended = false;
    }

    public Delivery(String orderNumber, String source, String destination, int distance) {  //Card constructor
        this.orderNumber = orderNumber;
        this.destination = destination;
        this.origin = source;
        this.distance = distance;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}

