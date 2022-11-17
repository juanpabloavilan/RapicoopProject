package co.edu.unipiloto.rapicoopproject.lib;

import co.edu.unipiloto.rapicoopproject.enums.OrderStatus;

public class Order {
    private int id;
    private final String clientId;
    private String orderTotal;
    private final String orderDate;
    private final String destination;
    private OrderStatus status;
    private String restaurantId;

    public Order(String clientId, String orderTotal, String orderDate, String destination, String restaurantId) {
        this.clientId = clientId;
        this.orderTotal = orderTotal;
        this.orderDate = orderDate;
        this.destination = destination;
        status = OrderStatus.INICIADA;
        this.restaurantId = restaurantId;
    }

    public int getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getDestination() {
        return destination;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
