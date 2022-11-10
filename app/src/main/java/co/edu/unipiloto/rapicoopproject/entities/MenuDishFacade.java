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
import co.edu.unipiloto.rapicoopproject.interfaces.IMenuDishFacade;
import co.edu.unipiloto.rapicoopproject.lib.MenuDish;

public class MenuDishFacade extends  AbstractFacade implements IMenuDishFacade {
    private static final String TAG = "MENU_DISH_FACADE";
    private static final String MENU_DISHES_TABLE_NAME = RapicoopDataBaseHelper.MENU_DISHES_TABLE_NAME;
    private static final String MENU_DISH_ID = RapicoopDataBaseHelper.MENU_DISH_ID;
    private static final String MENU_DISH_NAME = RapicoopDataBaseHelper.MENU_DISH_NAME;
    private static final String MENU_DISH_VENDOR_ID =RapicoopDataBaseHelper.MENU_DISH_VENDOR_ID;
    private static final String MENU_DISH_DESCRIPTION= RapicoopDataBaseHelper.MENU_DISH_DESCRIPTION;
    private static final String MENU_DISH_IMAGE = RapicoopDataBaseHelper.MENU_DISH_IMAGE;
    private static final String MENU_DISH_PRICE = RapicoopDataBaseHelper.MENU_DISH_PRICE;
    private static final String MENU_DISH_FOOD_CATEGORY = RapicoopDataBaseHelper.MENU_DISH_FOOD_CATEGORY;

    private RapicoopDataBaseHelper dataBaseHelper;
    private Context context;

    private static MenuDishFacade instance;


    public static MenuDishFacade getInstance(Context context){
        if(instance == null){
            instance = new MenuDishFacade();
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
    public long insertMenuDish(MenuDish menuDish) {
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        ContentValues menuDishDataSet = new ContentValues();
        menuDishDataSet.put(MENU_DISH_NAME, menuDish.getNombre());
        menuDishDataSet.put(MENU_DISH_DESCRIPTION, menuDish.getDescripcion());
        menuDishDataSet.put(MENU_DISH_PRICE, menuDish.getPrecio());
        menuDishDataSet.put(MENU_DISH_IMAGE, menuDish.getImage());
        menuDishDataSet.put(MENU_DISH_FOOD_CATEGORY, menuDish.getFoodCategory());
        menuDishDataSet.put(MENU_DISH_VENDOR_ID, menuDish.getRestaurantId());

        try{
            return db.insert(MENU_DISHES_TABLE_NAME,null,menuDishDataSet);

        }catch (Exception e){
            Log.e(TAG, "Error al insertar menu " + menuDish.toString() + "\n" + e.getMessage());
            return -1;
        }
    }

    @Override
    public MenuDish updateMenuDish(MenuDish menuDish) {
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        ContentValues menuDishDataSet = new ContentValues();
        menuDishDataSet.put(MENU_DISH_NAME, menuDish.getNombre());
        menuDishDataSet.put(MENU_DISH_DESCRIPTION, menuDish.getDescripcion());
        menuDishDataSet.put(MENU_DISH_PRICE, menuDish.getPrecio());
        menuDishDataSet.put(MENU_DISH_IMAGE, menuDish.getImage());
        menuDishDataSet.put(MENU_DISH_FOOD_CATEGORY, menuDish.getFoodCategory());
        menuDishDataSet.put(MENU_DISH_VENDOR_ID, menuDish.getRestaurantId());

        try{
            db.update(MENU_DISHES_TABLE_NAME, menuDishDataSet, MENU_DISH_ID + " = " + menuDish.getId(), null );
            return getMenuDishByID(menuDish.getId());
        }catch (Exception e){
            Log.e(TAG, "Error al actualizar el plato: " + menuDish.toString());
            return null;
        }
    }

    @Override
    public List<MenuDish> getMenuDishesByVendorID(int idInput) {
        String MENU_DISH_SELECT_QUERY = "SELECT * FROM "+ MENU_DISHES_TABLE_NAME + " " +
                "WHERE "+ MENU_DISH_VENDOR_ID +" = "+ idInput;
        System.out.println(MENU_DISH_SELECT_QUERY);
        List<MenuDish> menuDishes = new ArrayList<>();
        MenuDish currentMenuDish = null;
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        Cursor cursor = db.rawQuery(MENU_DISH_SELECT_QUERY, null);
        try{
            if(!cursor.moveToFirst())  return null;
            do{
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(MENU_DISH_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(MENU_DISH_NAME));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(MENU_DISH_DESCRIPTION));
                @SuppressLint("Range") String foodCategory = cursor.getString(cursor.getColumnIndex(MENU_DISH_FOOD_CATEGORY));
                @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex(MENU_DISH_IMAGE));
                @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndex(MENU_DISH_PRICE));
                @SuppressLint("Range") int vendorID = cursor.getInt(cursor.getColumnIndex(MENU_DISH_VENDOR_ID));
                currentMenuDish = new MenuDish(id, description, name, price, image);
                currentMenuDish.setRestaurantId(vendorID);
                currentMenuDish.setFoodCategory(foodCategory);
                menuDishes.add(currentMenuDish);
            }while (cursor.moveToNext());

        }catch (Exception e){
            Log.d(TAG, "Error al buscar platos por vendorID");
        }finally {
            if (cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return menuDishes;

    }

    @Override
    public MenuDish getMenuDishByID(int idInput) {
        String MENU_DISH_SELECT_QUERY = "SELECT * FROM "+ MENU_DISHES_TABLE_NAME + " " +
                "WHERE "+ MENU_DISH_ID +" = "+ idInput;
        System.out.println(MENU_DISH_SELECT_QUERY);
        MenuDish menuDish = null;
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        Cursor cursor = db.rawQuery(MENU_DISH_SELECT_QUERY, null);
        try{
            if(cursor.moveToFirst()){
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(MENU_DISH_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(MENU_DISH_NAME));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(MENU_DISH_DESCRIPTION));
                @SuppressLint("Range") String foodCategory = cursor.getString(cursor.getColumnIndex(MENU_DISH_FOOD_CATEGORY));
                @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex(MENU_DISH_IMAGE));
                @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndex(MENU_DISH_PRICE));
                @SuppressLint("Range") int vendorID = cursor.getInt(cursor.getColumnIndex(MENU_DISH_VENDOR_ID));
                menuDish = new MenuDish(id, description, name, price, image);
                menuDish.setRestaurantId(vendorID);
                menuDish.setFoodCategory(foodCategory);
            }
        }catch (Exception e){
            Log.d(TAG, "Error al buscar plato por ID");
        }finally {
            if (cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return menuDish;
    }


}
