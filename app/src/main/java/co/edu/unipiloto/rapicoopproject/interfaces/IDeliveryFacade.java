package co.edu.unipiloto.rapicoopproject.interfaces;

import co.edu.unipiloto.rapicoopproject.lib.Delivery;

public interface IDeliveryFacade {
    int getOrderIdByDeliver(int deliveryId);
    Delivery getDeliveryByDeliver(int userId);
    boolean hasActiveDelivery(int deliverId);
    void updateDeliveryEnded(int deliveryId);
}
