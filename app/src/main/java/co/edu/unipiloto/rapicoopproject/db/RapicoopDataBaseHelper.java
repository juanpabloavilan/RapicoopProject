package co.edu.unipiloto.rapicoopproject.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import co.edu.unipiloto.rapicoopproject.lib.Kitchen;
import co.edu.unipiloto.rapicoopproject.lib.KitchenLease;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class RapicoopDataBaseHelper extends SQLiteOpenHelper {

    private static RapicoopDataBaseHelper instance;

    //Database info
    public static final String DATABASE_NAME = "Rapicoop.db";
    public static final int DATABASE_VER = 1;
    private final String TAG = "RAPICOOPDATABASE_HELPER";

    //Users table
    public static final String USERS_TABLE_NAME = "users_table";
    public static final String USER_ID ="ID";
    public static final String USER_FULLNAME ="FULLNAME";
    public static final String USER_EMAIL ="EMAIL";
    public static final String USER_PHONE ="PHONE";
    public static final String USER_BIRTHDATE ="BIRTHDATE";
    public static final String USER_ADDRESS = "ADDRESS";
    public static final String USER_PASSWORD ="PASSWORD";
    public static final String USER_GENDER ="GENDER";
    public static final String USER_TYPE ="TYPE";

    //Kitchens table
    public static final String KITCHENS_TABLE_NAME = "kitchens_table";
    public static final String KITCHEN_ID ="ID";
    public static final String KITCHEN_NAME ="NAME";
    public static final String KITCHEN_ADDRESS ="ADDRESS";
    public static final String KITCHEN_LOCALITY ="LOCALITY";

    //Kitchen lease table
    public static final String LEASE_TABLE_NAME = "lease_table";
    public static final String LEASE_ID ="ID";
    public static final String LEASE_VENDOR_ID ="VENDOR_ID";
    public static final String LEASE_KITCHEN_ID ="KITCHEN_ID";
    public static final String LEASE_INI_DATE = "INI_DATE";
    public static final String LEASE_END_DATE = "END_DATE";

    //Restaurant table
    public static final String RESTAURANT_TABLE_NAME = "restaurant_table";
    public static final String RESTAURANT_ID = "ID";
    public static final String RESTAURANT_NAME = "NAME";
    public static final String RESTAURANT_TYPE = "TYPE";
    public static final String RESTAURANT_OWNER_ID = "OWNER_ID";

    //Menu Dishes table
    public static final String MENU_DISHES_TABLE_NAME = "menu_dishes_table";
    public static final String MENU_DISH_ID ="ID";
    public static final String MENU_DISH_NAME ="NAME";
    public static final String MENU_DISH_VENDOR_ID ="VENDOR_ID";
    public static final String MENU_DISH_DESCRIPTION="DESCRIPTION";
    public static final String MENU_DISH_IMAGE = "IMAGE";
    public static final String MENU_DISH_PRICE = "PRICE";
    public static final String MENU_DISH_FOOD_CATEGORY = "FOOD_CATEGORY";

    //ItemOrder table
    public static final String ORDER_ITEM_TABLE_NAME = "order_items_table";
    public static final String ORDER_ITEM_ID ="ID";
    public static final String ORDER_ITEM_DISH_ID ="DISH_ID";
    public static final String ORDER_ITEM_ORDER_ID ="ORDER_ID";

    //Orders table
    public static final String ORDER_TABLE_NAME = "orders_table";
    public static final String ORDER_ID ="ID";
    public static final String ORDER_CLIENT_ID ="CLIENT_ID";
    public static final String ORDER_DELIVER_ID ="DELIVER_ID";
    public static final String ORDER_TOTAL ="TOTAL";
    public static final String ORDER_DATE ="DATE";
    public static final String ORDER_DESTINATION ="DESTINATION";
    public static final String ORDER_SOURCE ="SOURCE";
    public static final String ORDER_ENDED ="ENDED";

    
    /**
     * Constructor privado que crea la instancias de RapicoopDataBaseHelper.
     * @param context
     */
    private RapicoopDataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    /**
     * Retorna la instancia y la crea si no ha sido creada.
     * @param context
     * @return RapicoopDataBaseHelper
     */
    public static synchronized RapicoopDataBaseHelper getInstance(Context context){
        if(instance == null){ //Si no existe la instancia la creamos por primera y única vez
            instance = new RapicoopDataBaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USERS_TABLE = "CREATE TABLE "+ USERS_TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FULLNAME TEXT NOT NULL," +
                "EMAIL TEXT NOT NULL UNIQUE," +
                "PHONE TEXT NOT NULL," +
                "ADDRESS TEXT NOT NULL," +
                "BIRTHDATE TEXT NOT NULL," +
                "PASSWORD TEXT NOT NULL," +
                "GENDER TEXT NOT NULL," +
                "TYPE TEXT NOT NULL)";

        String CREATE_KITCHENS_TABLE = "CREATE TABLE "+ KITCHENS_TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT NOT NULL," +
                "ADDRESS TEXT NOT NULL UNIQUE," +
                "LOCALITY TEXT NOT NULL, " +
                "DESCRIPTION TEXT)";

        String CREATE_LEASE_TABLE = "CREATE TABLE " + LEASE_TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "VENDOR_ID	INTEGER NOT NULL," +
                "KITCHEN_ID	INTEGER NOT NULL," +
                "INI_DATE TEXT NOT NULL," +
                "END_DATE TEXT NOT NULL," +
                "FOREIGN KEY(VENDOR_ID) REFERENCES "+USERS_TABLE_NAME+"(id)," +
                "FOREIGN KEY(KITCHEN_ID) REFERENCES "+KITCHENS_TABLE_NAME+"(id))";

        String CREATE_MENU_DISHES_TABLE = "CREATE TABLE " + MENU_DISHES_TABLE_NAME + "("+
                MENU_DISH_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                MENU_DISH_NAME + " TEXT NOT NULL," +
                MENU_DISH_DESCRIPTION+" TEXT NOT NULL," +
                MENU_DISH_FOOD_CATEGORY+" TEXT NOT NULL," +
                MENU_DISH_IMAGE + " INTEGER," +
                MENU_DISH_PRICE + " INTEGER, "+
                MENU_DISH_VENDOR_ID + " INTEGER NOT NULL,"+
                "FOREIGN KEY ("+MENU_DISH_VENDOR_ID + ") REFERENCES "+ USERS_TABLE_NAME +"("+USER_ID+"))";

        String CREATE_RESTAURANTS_TABLE = "CREATE TABLE " + RESTAURANT_TABLE_NAME + "("+
                RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RESTAURANT_NAME + " TEXT NOT NULL UNIQUE," +
                RESTAURANT_TYPE + " TEXT NOT NULL UNIQUE," +
                RESTAURANT_OWNER_ID + " INTEGER NOT NULL," +
                "FOREIGN KEY ("+RESTAURANT_OWNER_ID+") REFERENCES "+ USERS_TABLE_NAME +"("+USER_ID+"))";

        String CREATE_ORDER_ITEMS_TABLE = "CREATE TABLE " + ORDER_ITEM_TABLE_NAME + "("+
                ORDER_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ORDER_ITEM_DISH_ID + " INTEGER NOT NULL," +
                ORDER_ITEM_ORDER_ID + " INTEGER NOT NULL," +
                "FOREIGN KEY ("+ORDER_ITEM_DISH_ID+") REFERENCES "+ MENU_DISHES_TABLE_NAME +"("+MENU_DISH_ID+")," +
                "FOREIGN KEY ("+ORDER_ITEM_ORDER_ID+") REFERENCES "+ ORDER_TABLE_NAME +"("+ORDER_ID+"))";

        String CREATE_ORDERS_TABLE = "CREATE TABLE " + ORDER_TABLE_NAME + "("+
                ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ORDER_CLIENT_ID + " INTEGER NOT NULL," +  //CLIENTE
                ORDER_DELIVER_ID + " INTEGER," +
                ORDER_SOURCE + " TEXT," +
                ORDER_DESTINATION + " TEXT," +
                ORDER_TOTAL + " TEXT NOT NULL," +   //
                ORDER_DATE + " INTEGER NOT NULL, " +   //
                ORDER_ENDED + " INTEGER, " +
                "FOREIGN KEY ("+ORDER_CLIENT_ID+") REFERENCES "+ USERS_TABLE_NAME +"("+USER_ID+")," +
                "FOREIGN KEY ("+ORDER_DELIVER_ID+") REFERENCES "+ USERS_TABLE_NAME +"("+USER_ID+"))";

        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(CREATE_KITCHENS_TABLE);
        sqLiteDatabase.execSQL(CREATE_LEASE_TABLE);
        sqLiteDatabase.execSQL(CREATE_MENU_DISHES_TABLE);
        sqLiteDatabase.execSQL(CREATE_RESTAURANTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_ORDER_ITEMS_TABLE);
        sqLiteDatabase.execSQL(CREATE_ORDERS_TABLE);
    }

    private void populateKitchens(){
        String[] kitchenValues= Faker.getKitchenValues();
        SQLiteDatabase db= getWritableDatabase();
        ContentValues values = new ContentValues();
        for (String value:kitchenValues) {
            String[] params = value.split(",");
            values.put(KITCHEN_ID, params[0]);
            values.put(KITCHEN_NAME, params[1]);
            values.put(KITCHEN_ADDRESS, params[2]);
            values.put(KITCHEN_LOCALITY, params[3]);

            db.insert(KITCHENS_TABLE_NAME, null, values);
            values.clear();
        }
    }

    private void populateUsers(){
        String [] usersValues = Faker.getUsersValues();
        SQLiteDatabase db= getWritableDatabase();
        ContentValues values = new ContentValues();
        for (String user : usersValues){
            String [] params = user.split(",");
            values.put(USER_ID, params[0]);
            values.put(USER_FULLNAME, params[1]);
            values.put(USER_EMAIL, params[2]);
            values.put(USER_PHONE, params[3]);
            values.put(USER_ADDRESS, params[4] );
            values.put(USER_BIRTHDATE, params[5]);
            values.put(USER_PASSWORD, params[6]);
            values.put(USER_GENDER, params[7]);
            values.put(USER_TYPE, params[8]);

            db.insert(USERS_TABLE_NAME, null, values);
            values.clear();
        }

    }

    private void populateMenus(){
        String [] menuDishesValues = Faker.getMenuDishesValues();
        SQLiteDatabase db= getWritableDatabase();
        ContentValues values = new ContentValues();
        for (String menuDishes : menuDishesValues) {
            String[] params = menuDishes.split(",");
            values.put(MENU_DISH_ID, params[0]);
            values.put(MENU_DISH_NAME, params[1]);
            values.put(MENU_DISH_DESCRIPTION, params[2]);
            values.put(MENU_DISH_FOOD_CATEGORY, params[3]);
            values.put(MENU_DISH_VENDOR_ID, Integer.parseInt(params[4]));
            values.put(MENU_DISH_PRICE, Integer.parseInt(params[5]));
            db.insert(MENU_DISHES_TABLE_NAME, null, values);
            values.clear();
        }
    }

    private void populateRestaurants(){
        String [] restaurantValues = Faker.getRestaurantValues();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        for(String restaurantData : restaurantValues){
            String[] params = restaurantData.split(",");
            values.put(RESTAURANT_ID, params[0]);
            values.put(RESTAURANT_NAME, params[1]);
            values.put(RESTAURANT_TYPE, params[2]);
            values.put(RESTAURANT_OWNER_ID, params[3]);
            db.insert(RESTAURANT_TABLE_NAME,null,values);
            values.clear();
        }
    }

    private void populateOrders(){
        String [] orderValues = Faker.getOrderValues();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        for(String orderData : orderValues){
            String[] params = orderData.split(",");
            values.put(ORDER_ID, params[0]);
            values.put(ORDER_CLIENT_ID, params[1]);
            values.put(ORDER_DELIVER_ID, params[2]);
            values.put(ORDER_TOTAL, params[3]);
            values.put(ORDER_DATE, params[4]);
            values.put(ORDER_SOURCE, params[5] + "," + params[6]);
            values.put(ORDER_DESTINATION, params[7] + "," + params[8]);
            values.put(ORDER_ENDED, params[9]);
            db.insert(ORDER_TABLE_NAME,null,values);
            values.clear();
        }
    }

    private void populateOrderItems(){
        String [] orderItemValues = Faker.getItemOrderValues();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        for(String itemData : orderItemValues){
            String[] params = itemData.split(",");
            int id = Integer.parseInt(params[0]);
            values.put(ORDER_ITEM_ID, id);
            values.put(ORDER_ITEM_DISH_ID, params[1]);
            values.put(ORDER_ITEM_ORDER_ID, params[2]);
            db.insert(ORDER_ITEM_TABLE_NAME,null,values);
            values.clear();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ USERS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ RESTAURANT_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ KITCHENS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ LEASE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MENU_DISHES_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ ORDER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ ORDER_ITEM_TABLE_NAME);
        onCreate(sqLiteDatabase);
        populateKitchens();
        populateUsers();
        populateMenus();
        populateRestaurants();
        populateOrders();
        populateOrderItems();
    }

    public void initDb(){
        SQLiteDatabase db=this.getWritableDatabase();
        onUpgrade(db,1,1);
    }



}
