package co.edu.unipiloto.rapicoopproject.entities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;
import co.edu.unipiloto.rapicoopproject.enums.OrderStatus;
import co.edu.unipiloto.rapicoopproject.interfaces.IOrderFacade;
import co.edu.unipiloto.rapicoopproject.lib.Order;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class OrderFacade extends AbstractFacade implements IOrderFacade {
    private final String TAG = "ORDER_FACADE";
    public static final String ORDER_TABLE_NAME = "orders_table";
    public static final String ORDER_ID ="ID";
    public static final String ORDER_CLIENT_ID ="CLIENT_ID";
    public static final String ORDER_RESTAURANT_ID = "RESTAURANT_ID";
    public static final String ORDER_TOTAL ="TOTAL";
    public static final String ORDER_DATE ="DATE";
    public static final String ORDER_STATUS = "STATUS";

    private Context context;

    private static OrderFacade instance;

    public static OrderFacade getInstance(Context context){
        if(instance == null){
            instance = new OrderFacade();
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

    public List<Order> getPendingOrders() {
        String PENDING_ORDERS_QUERY = " SELECT * FROM " + ORDER_TABLE_NAME + " WHERE " +
                ORDER_STATUS + " = '" + OrderStatus.INICIADA +"'";
        SQLiteDatabase db = getDatabaseHelper(instance.context).getReadableDatabase();
        Cursor cursor = db.rawQuery(PENDING_ORDERS_QUERY, null);
        ArrayList<Order> pendingOrders = new ArrayList<>();
        while(cursor.moveToNext()){
            @SuppressLint("Range") String idString = cursor.getString(cursor.getColumnIndex(ORDER_ID));
            @SuppressLint("Range") String clientId = cursor.getString(cursor.getColumnIndex(ORDER_CLIENT_ID));
            @SuppressLint("Range") String restaurantId = cursor.getString(cursor.getColumnIndex(ORDER_RESTAURANT_ID));
            @SuppressLint("Range") String total = cursor.getString(cursor.getColumnIndex(ORDER_TOTAL));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(ORDER_DATE));
            int id = Integer.parseInt(idString);
            pendingOrders.add(new Order(id,clientId,total,date,OrderStatus.INICIADA,restaurantId));
        }
        cursor.close();
        return pendingOrders;
    }

    public Order getOrderById(int orderId) {
        String ORDER_QUERY = " SELECT * FROM " + ORDER_TABLE_NAME + " WHERE " +
                ORDER_ID + " = " + orderId;
        SQLiteDatabase db = getDatabaseHelper(instance.context).getReadableDatabase();
        Cursor cursor = db.rawQuery(ORDER_QUERY, null);
        Order currentOrder;
        if(cursor.moveToFirst()){
            @SuppressLint("Range") String idString = cursor.getString(cursor.getColumnIndex(ORDER_ID));
            @SuppressLint("Range") String clientId = cursor.getString(cursor.getColumnIndex(ORDER_CLIENT_ID));
            @SuppressLint("Range") String restaurantId = cursor.getString(cursor.getColumnIndex(ORDER_RESTAURANT_ID));
            @SuppressLint("Range") String total = cursor.getString(cursor.getColumnIndex(ORDER_TOTAL));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(ORDER_DATE));
            @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex(ORDER_STATUS));
            cursor.close();
            int id = Integer.parseInt(idString);
            return new Order(id,clientId,total,date,OrderStatus.valueOf(status),restaurantId);
        }
        cursor.close();
        return null;
    }

    public long insertOrder(Order newOrder){
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        ContentValues orderDataSet = new ContentValues();
        orderDataSet.put(ORDER_CLIENT_ID,newOrder.getClientId());
        orderDataSet.put(ORDER_TOTAL,newOrder.getOrderTotal());
        orderDataSet.put(ORDER_DATE,newOrder.getOrderDate());
        orderDataSet.put(ORDER_RESTAURANT_ID,newOrder.getRestaurantId());
        orderDataSet.put(ORDER_STATUS,newOrder.getStatus().toString());
        return db.insert(ORDER_TABLE_NAME, null, orderDataSet);
    }

    public void updateOrderStatus(String orderId, OrderStatus status) {
        SQLiteDatabase db = RapicoopDataBaseHelper.getInstance(instance.context).getWritableDatabase();
        ContentValues orderData = new ContentValues();
        orderData.put(ORDER_STATUS,status.toString());
        try{
            db.update(ORDER_TABLE_NAME,orderData,ORDER_ID+" = ?", new String[]{ orderId });
        }catch (Exception e){
            Log.e(TAG, "Error al actualizar el pedido: " + orderId);
        }
    }

    public int getOrderClientId(int orderId){
        String CLIENT_QUERY = " SELECT " + ORDER_CLIENT_ID +" FROM " + ORDER_TABLE_NAME + " WHERE " + ORDER_ID + " = " + orderId;
        SQLiteDatabase db = getDatabaseHelper(instance.context).getReadableDatabase();
        Cursor cursor = db.rawQuery(CLIENT_QUERY, null);
        if(cursor.moveToFirst()){
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ORDER_CLIENT_ID));
            cursor.close();
            return Integer.parseInt(id);
        }
        cursor.close();
        return -1; //NOT FOUND
    }
}
