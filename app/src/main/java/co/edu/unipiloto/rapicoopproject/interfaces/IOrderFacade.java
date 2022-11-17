package co.edu.unipiloto.rapicoopproject.interfaces;

import co.edu.unipiloto.rapicoopproject.lib.Order;

public interface IOrderFacade {
    Order createOrder();
    void prepareOrderFood();
    void acceptOrderDelivery ();
    void pickUpOrderDelivery();
    void deliverOrder();
}
