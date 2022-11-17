package co.edu.unipiloto.rapicoopproject.entities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;
import co.edu.unipiloto.rapicoopproject.interfaces.IDeliveryFacade;
import co.edu.unipiloto.rapicoopproject.lib.Delivery;
import co.edu.unipiloto.rapicoopproject.lib.Order;

public class DeliveryFacade extends AbstractFacade implements IDeliveryFacade {
    private final String TAG = "DELIVERY_FACADE";
    public static final String DELIVERY_TABLE_NAME = "deliveries_table";
    public static final String DELIVERY_ID ="ID";
    public static final String DELIVERY_ORDER_ID = "ORDER_ID";
    public static final String DELIVERY_GUY_ID = "DELIVERY_ID";
    public static final String DELIVERY_SOURCE = "SOURCE";
    public static final String DELIVERY_ENDED = "ENDED";


    private RapicoopDataBaseHelper dataBaseHelper;
    private Context context;

    private static DeliveryFacade instance;

    public static DeliveryFacade getInstance(Context context){
        if(instance == null){
            instance = new DeliveryFacade();
        }
        instance.setContext(context);
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected RapicoopDataBaseHelper getDatabaseHelper(Context context) {
        return RapicoopDataBaseHelper.getInstance(context);
    }

    public long insertDelivery(Delivery newDelivery){
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        ContentValues deliveryDataSet = new ContentValues();
        deliveryDataSet.put(DELIVERY_ORDER_ID,newDelivery.getOrderNumber());
        deliveryDataSet.put(DELIVERY_GUY_ID,newDelivery.getDeliverId());
        deliveryDataSet.put(DELIVERY_SOURCE,newDelivery.getOrigin());
        deliveryDataSet.put(DELIVERY_SOURCE,newDelivery.getDestination());
        return db.insert(DELIVERY_TABLE_NAME, null, deliveryDataSet);
    }

    public int getOrderIdByDeliver(int deliveryId){
        String ORDER_QUERY = " SELECT " + DELIVERY_ORDER_ID + " FROM " + DELIVERY_TABLE_NAME +
                " WHERE " + DELIVERY_GUY_ID + " = " + deliveryId;
        ContentValues orderDataSet = new ContentValues();
        SQLiteDatabase db = getDatabaseHelper(instance.context).getReadableDatabase();
        Cursor cursor = db.rawQuery(ORDER_QUERY, null);
        int orderId = -1;
        if(cursor.moveToFirst()){
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(DELIVERY_ORDER_ID));
            orderId = Integer.parseInt(id);
        }
        cursor.close();
        return orderId;
    }
}
