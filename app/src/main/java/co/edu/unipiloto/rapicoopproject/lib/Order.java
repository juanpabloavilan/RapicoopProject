package co.edu.unipiloto.rapicoopproject.lib;

public class Order {
    private String orderNumber;
    private String destination;
    private String origin;
    private int distance;

    public Order(String orderNumber, String destination, String origin, int distance) {
        this.orderNumber = orderNumber;
        this.destination = destination;
        this.origin = origin;
        this.distance = distance;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
}
