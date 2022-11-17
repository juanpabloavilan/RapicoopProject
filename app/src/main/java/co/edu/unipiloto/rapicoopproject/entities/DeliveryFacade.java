package co.edu.unipiloto.rapicoopproject.entities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;
import co.edu.unipiloto.rapicoopproject.interfaces.IDeliveryFacade;

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

    public double[] getDeliveryTarget(int idDeliver){
        String ORDER_QUERY = " SELECT * FROM " + DELIVERY_TABLE_NAME + " WHERE " + DELIVERY_GUY_ID + " = " + idDeliver;
        SQLiteDatabase db = getDatabaseHelper(instance.context).getReadableDatabase();
        Cursor cursor = db.rawQuery(ORDER_QUERY, null);
        double[] indications = null;
        if(cursor.moveToFirst()){
            @SuppressLint("Range") String destination = cursor.getString(cursor.getColumnIndex(DELIVERY_DESTINATION));
            String[] arrDestination = destination.split(",");
            indications = new double[] {Double.parseDouble(arrDestination[0]),Double.parseDouble(arrDestination[1])};
        }
        return indications;
    }
}
