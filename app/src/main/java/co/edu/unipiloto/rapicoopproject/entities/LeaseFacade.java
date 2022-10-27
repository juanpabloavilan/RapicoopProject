package co.edu.unipiloto.rapicoopproject.entities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;
import co.edu.unipiloto.rapicoopproject.interfaces.ILeaseFacade;
import co.edu.unipiloto.rapicoopproject.lib.Kitchen;
import co.edu.unipiloto.rapicoopproject.lib.KitchenLease;

public class LeaseFacade extends AbstractFacade implements ILeaseFacade {

    /*
    Lease Table Structure
    public static final String LEASE_TABLE_NAME = "lease_table";
    public static final String LEASE_ID ="ID";
    public static final String LEASE_VENDOR_ID ="VENDOR_ID";
    public static final String LEASE_KITCHEN_ID ="KITCHEN_ID";
    public static final String LEASE_INI_DATE = "INI_DATE";
    public static final String LEASE_END_DATE = "END_DATE";
     */

    private RapicoopDataBaseHelper dataBaseHelper;
    private Context context;

    private static LeaseFacade instance;


    public static LeaseFacade getInstance(Context context){
        if(instance == null){
            instance = new LeaseFacade();
        }
        instance.setContext(context);
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected RapicoopDataBaseHelper getDatabaseHelper(Context context) {
        dataBaseHelper = RapicoopDataBaseHelper.getInstance(context);
        return dataBaseHelper;
    }


    @Override
    public String[] getAllKitchenLocalities() {
        String LOCALITY_SELECT_QUERY = "SELECT DISTINCT " + RapicoopDataBaseHelper.KITCHEN_LOCALITY + " FROM " + RapicoopDataBaseHelper.KITCHENS_TABLE_NAME;
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        Cursor localitiesCursor = db.rawQuery(LOCALITY_SELECT_QUERY,null);
        ArrayList<String> localities = new ArrayList<String>();
        while(localitiesCursor.moveToNext()) {
            @SuppressLint("Range") String locality = localitiesCursor.getString(localitiesCursor.getColumnIndex(RapicoopDataBaseHelper.KITCHEN_LOCALITY));
            localities.add(locality);
        }
        localitiesCursor.close();
        return localities.toArray(new String[0]);
    }

    @Override
    public Kitchen[] getAllKitchensByLocality(String targetLocality) {
        String KITCHEN_SELECT_QUERY = "SELECT * FROM " + RapicoopDataBaseHelper.KITCHENS_TABLE_NAME +
                " WHERE " + RapicoopDataBaseHelper.KITCHEN_LOCALITY + " = '" + targetLocality + "'";
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        Cursor kitchensCursor = db.rawQuery(KITCHEN_SELECT_QUERY,null);
        ArrayList<Kitchen> kitchens = new ArrayList<Kitchen>();
        while(kitchensCursor.moveToNext()){
            @SuppressLint("Range") int id = Integer.parseInt(kitchensCursor.getString(kitchensCursor.getColumnIndex(RapicoopDataBaseHelper.KITCHEN_ID)));
            @SuppressLint("Range") String name = kitchensCursor.getString(kitchensCursor.getColumnIndex(RapicoopDataBaseHelper.KITCHEN_NAME));
            @SuppressLint("Range") String address = kitchensCursor.getString(kitchensCursor.getColumnIndex(RapicoopDataBaseHelper.KITCHEN_ADDRESS));
            @SuppressLint("Range") String locality = kitchensCursor.getString(kitchensCursor.getColumnIndex(RapicoopDataBaseHelper.KITCHEN_LOCALITY));
            kitchens.add(new Kitchen(id, name, address, locality));
        }
        kitchensCursor.close();
        return kitchens.toArray(new Kitchen[0]);
    }

    @Override
    public long insertLease(KitchenLease lease) {
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        ContentValues leaseDataSet = new ContentValues();
        leaseDataSet.put(RapicoopDataBaseHelper.LEASE_VENDOR_ID,lease.getVendorId());
        leaseDataSet.put(RapicoopDataBaseHelper.LEASE_KITCHEN_ID,lease.getKitchenId());
        leaseDataSet.put(RapicoopDataBaseHelper.LEASE_INI_DATE,lease.getIniDate());
        leaseDataSet.put(RapicoopDataBaseHelper.LEASE_END_DATE,lease.getEndDate());

        return db.insert(RapicoopDataBaseHelper.LEASE_TABLE_NAME,null,leaseDataSet);
    }

    @Override
    public boolean leaseAvailability(int kitchenId, int vendorId) {
        String LEASE_SELECT_QUERY = "SELECT * FROM "+ RapicoopDataBaseHelper.LEASE_TABLE_NAME +
                " WHERE "+ RapicoopDataBaseHelper.LEASE_KITCHEN_ID +" = "+ kitchenId +
                " OR " + RapicoopDataBaseHelper.LEASE_VENDOR_ID +" = "+ vendorId;
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        Cursor leaseMatch = db.rawQuery(LEASE_SELECT_QUERY,null);
        if(leaseMatch.moveToFirst()){ //O una cocina ya esta arrendada o un vendedor ya tiene cocina
            return false;
        }
        leaseMatch.close();
        return true;
    }
}
