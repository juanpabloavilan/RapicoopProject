package co.edu.unipiloto.rapicoopproject.lib;

import co.edu.unipiloto.rapicoopproject.enums.OrderStatus;

public class Order {
    private int id;
    private final String clientId;
    private String orderTotal;
    private final String orderDate;
    private OrderStatus status;
    private final String restaurantId;

    public Order(String clientId, String orderTotal, String orderDate, String restaurantId) {
        this.clientId = clientId;
        this.orderTotal = orderTotal;
        this.orderDate = orderDate;
        status = OrderStatus.INICIADA;
        this.restaurantId = restaurantId;
    }

    public Order(int id, String clientId, String orderTotal, String orderDate, OrderStatus status, String restaurantId) {
        this.id = id;
        this.clientId = clientId;
        this.orderTotal = orderTotal;
        this.orderDate = orderDate;
        this.status = status;
        this.restaurantId = restaurantId;
    }

    public int getId() {
        return id;
    }

    public String getRestaurantId() {
        return restaurantId;
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

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
