package co.edu.unipiloto.rapicoopproject.lib;

public class Delivery {
    private String id;
    private String orderNumber;
    private String deliverId;
    private String origin;
    private String destination;
    private int distance;
    private boolean ended;

    public Delivery(String id, String orderNumber, String deliverId, String source, String destination) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.deliverId = deliverId;
        this.origin = source;
        this.destination = destination;
        this.ended = false;
    }

    public Delivery(String orderNumber, String source, String destination, int distance) {  //Card constructor
        this.orderNumber = orderNumber;
        this.origin = source;
        this.destination = destination;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
}

