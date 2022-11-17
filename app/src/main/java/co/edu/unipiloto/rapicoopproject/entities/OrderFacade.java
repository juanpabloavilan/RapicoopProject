package co.edu.unipiloto.rapicoopproject.entities;

import android.content.Context;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;
import co.edu.unipiloto.rapicoopproject.interfaces.IOrderFacade;
import co.edu.unipiloto.rapicoopproject.lib.Order;

public class OrderFacade extends AbstractFacade implements IOrderFacade {

    @Override
    protected RapicoopDataBaseHelper getDatabaseHelper(Context context) {
        return null;
    }

    @Override
    public Order createOrder() {
        return null;
    }

    @Override
    public void prepareOrderFood() {

    }

    @Override
    public void acceptOrderDelivery() {

    }

    @Override
    public void pickUpOrderDelivery() {

    }

    @Override
    public void deliverOrder() {

    }
}
