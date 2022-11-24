package co.edu.unipiloto.rapicoopproject.entities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;
import co.edu.unipiloto.rapicoopproject.interfaces.IDeliveryFacade;
import co.edu.unipiloto.rapicoopproject.lib.Delivery;

public class DeliveryFacade extends AbstractFacade implements IDeliveryFacade {
    private final String TAG = "DELIVERY_FACADE";
    public static final String DELIVERY_TABLE_NAME = "deliveries_table";
    public static final String DELIVERY_ID ="ID";
    public static final String DELIVERY_ORDER_ID = "ORDER_ID";
    public static final String DELIVERY_GUY_ID = "DELIVERY_ID";
    public static final String DELIVERY_SOURCE = "SOURCE";
    public static final String DELIVERY_DESTINATION = "DESTINATION";
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
        deliveryDataSet.put(DELIVERY_SOURCE,newDelivery.getOriginStringCoords());
        deliveryDataSet.put(DELIVERY_SOURCE,newDelivery.getDestinationStringCoords());
        return db.insert(DELIVERY_TABLE_NAME, null, deliveryDataSet);
    }

    public Delivery getDeliveryByDeliver(int userId){
        String DELIVERY_QUERY = "SELECT * FROM "+ DELIVERY_TABLE_NAME + " " +
                    "WHERE "+ DELIVERY_GUY_ID +" = "+ userId;
        SQLiteDatabase db = getDatabaseHelper(instance.context).getReadableDatabase();
        Cursor cursor = db.rawQuery(DELIVERY_QUERY, null);
        if(cursor.moveToFirst()){
            @SuppressLint("Range") int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DELIVERY_ID)));
            @SuppressLint("Range") String orderId = cursor.getString(cursor.getColumnIndex(DELIVERY_ORDER_ID));
            @SuppressLint("Range") String deliverId = cursor.getString(cursor.getColumnIndex(DELIVERY_GUY_ID));
            @SuppressLint("Range") String origin = cursor.getString(cursor.getColumnIndex(DELIVERY_SOURCE));
            @SuppressLint("Range") String destination = cursor.getString(cursor.getColumnIndex(DELIVERY_DESTINATION));
            @SuppressLint("Range") String ended_string = cursor.getString(cursor.getColumnIndex(DELIVERY_ENDED));
            cursor.close();
            boolean ended = ended_string.equals("1");
            return new Delivery(id,orderId,deliverId,origin,destination,ended);
        }
        cursor.close();
        return null;
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
