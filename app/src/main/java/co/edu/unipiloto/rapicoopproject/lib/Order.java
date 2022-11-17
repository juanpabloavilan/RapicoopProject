package co.edu.unipiloto.rapicoopproject.lib;

public class Order {
    private String id;
    private String clientId;
    private String deliverId;
    private String source;
    private String destination;
    private boolean ended;
    private String orderTotal;
    private String orderDate;

    public Order(String id, String clientId, String deliverId, String source, String destination, String ended, String orderTotal, String orderDate) {
        this.id = id;
        this.clientId = clientId;
        this.deliverId = deliverId;
        this.source = source;
        this.destination = destination;
        this.ended = ended.equals("1");
        this.orderTotal = orderTotal;
        this.orderDate = orderDate;
    }

    public Order(String clientId, String orderTotal, String orderDate) {
        this.id = id;
        this.clientId = clientId;
        this.orderTotal = orderTotal;
        this.orderDate = orderDate;
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
