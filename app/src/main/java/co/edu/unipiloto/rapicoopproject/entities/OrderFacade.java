package co.edu.unipiloto.rapicoopproject.entities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;
import co.edu.unipiloto.rapicoopproject.interfaces.IOrderFacade;
import co.edu.unipiloto.rapicoopproject.lib.Order;

public class OrderFacade extends AbstractFacade implements IOrderFacade {
    private final String TAG = "ORDER_FACADE";
    public static final String ORDER_TABLE_NAME = "orders_table_name";
    public static final String ORDER_ID ="ID";
    public static final String ORDER_CLIENT_ID ="CLIENT_ID";
    public static final String ORDER_DELIVER_ID ="DELIVER_ID";
    public static final String ORDER_TOTAL ="TOTAL";
    public static final String ORDER_DATE ="DATE";
    public static final String ORDER_DESTINATION ="DESTINATION";
    public static final String ORDER_SOURCE ="SOURCE";
    public static final String ORDER_ENDED ="ENDED";

    private RapicoopDataBaseHelper dataBaseHelper;
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

    public long insertOrder(Order newOrder){
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        ContentValues orderDataSet = new ContentValues();
        orderDataSet.put(ORDER_CLIENT_ID,newOrder.getClientId());
        orderDataSet.put(ORDER_TOTAL,newOrder.getOrderTotal());
        orderDataSet.put(ORDER_DATE,newOrder.getOrderDate());
        return db.insert(ORDER_TABLE_NAME, null, orderDataSet);
    }

    public double[] getDeliveryTarget(int idDeliver){
        String ORDER_QUERY = " SELECT * FROM " + ORDER_TABLE_NAME + " WHERE " + ORDER_DELIVER_ID + " = " + idDeliver;
        ContentValues orderDataSet = new ContentValues();
        SQLiteDatabase db = getDatabaseHelper(instance.context).getReadableDatabase();
        Cursor cursor = db.rawQuery(ORDER_QUERY, null);
        double[] indications = null;
        if(cursor.moveToFirst()){
            @SuppressLint("Range") String destination = cursor.getString(cursor.getColumnIndex(ORDER_DESTINATION));
            String[] arrDestination = destination.split(",");
            indications = new double[] {Double.parseDouble(arrDestination[0]),Double.parseDouble(arrDestination[1])};
        }
        return indications;
    }
}
