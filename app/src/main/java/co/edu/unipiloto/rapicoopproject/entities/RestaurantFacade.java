package co.edu.unipiloto.rapicoopproject.entities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;
import co.edu.unipiloto.rapicoopproject.interfaces.IRestaurantFacade;
import co.edu.unipiloto.rapicoopproject.lib.Restaurant;

public class RestaurantFacade extends AbstractFacade implements IRestaurantFacade {
    private final String TAG = "RESTAURANT_FACADE";
    public static String RESTAURANT_TABLE_NAME = "RESTAURANT_TABLE";
    public static String RESTAURANT_ID = "ID";
    public static String RESTAURANT_NAME = "NAME";
    public static String RESTAURANT_TYPE = "TYPE";
    public static String RESTAURANT_OWNER_ID = "OWNER_ID";

    private RapicoopDataBaseHelper dataBaseHelper;
    private Context context;

    private static RestaurantFacade instance;

    public static RestaurantFacade getInstance(Context context){
        if(instance == null){
            instance = new RestaurantFacade();
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

    public long insertRestaurant(Restaurant restaurant){
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        ContentValues restaurantDataSet = new ContentValues();
        restaurantDataSet.put(RESTAURANT_NAME,restaurant.getName());
        restaurantDataSet.put(RESTAURANT_TYPE,restaurant.getType());
        restaurantDataSet.put(RESTAURANT_OWNER_ID,restaurant.getOwnerId());
        return db.insert(RESTAURANT_TABLE_NAME, null, restaurantDataSet);
    }

    public boolean hasRestaurant(int possibleOwnerId){
        String RESTAURANT_QUERY = "SELECT * FROM " + RESTAURANT_TABLE_NAME + " WHERE OWNER_ID = " + possibleOwnerId;
        SQLiteDatabase db = getDatabaseHelper(instance.context).getReadableDatabase();
        Cursor cursor = db.rawQuery(RESTAURANT_QUERY, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public List<Restaurant> getRestaurants(){
        String ALL_RESTAURANTS_QUERY = " SELECT * FROM " + RESTAURANT_TABLE_NAME;
        SQLiteDatabase db = getDatabaseHelper(instance.context).getReadableDatabase();
        Cursor cursor = db.rawQuery(ALL_RESTAURANTS_QUERY, null);
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(RESTAURANT_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(RESTAURANT_NAME));
            @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(RESTAURANT_TYPE));
            @SuppressLint("Range") String ownerId = cursor.getString(cursor.getColumnIndex(RESTAURANT_OWNER_ID));
            restaurants.add(new Restaurant(Integer.parseInt(id),name,type,Integer.parseInt(ownerId)));
        }
        cursor.close();
        return restaurants;
    }
}
