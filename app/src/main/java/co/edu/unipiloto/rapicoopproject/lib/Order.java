package co.edu.unipiloto.rapicoopproject.lib;

import co.edu.unipiloto.rapicoopproject.formats.OrderStatus;

public class Order {
    private String id;
    private final String clientId;
    private String orderTotal;
    private final String orderDate;
    private OrderStatus status;

    public Order(String clientId, String orderTotal, String orderDate) {
        this.id = id;
        this.clientId = clientId;
        this.orderTotal = orderTotal;
        this.orderDate = orderDate;
        status = OrderStatus.INICIADA;
    }

    public String getId() {
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

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
