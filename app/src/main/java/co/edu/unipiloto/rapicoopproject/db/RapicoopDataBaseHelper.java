package co.edu.unipiloto.rapicoopproject.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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

    //Menu Dishes table
    public static final String MENU_DISHES_TABLE_NAME = "menu_dishes_table";
    public static final String MENU_DISH_ID ="ID";
    public static final String MENU_DISH_NAME ="NAME";
    public static final String MENU_DISH_VENDOR_ID ="VENDOR_ID";
    public static final String MENU_DISH_DESCRIPTION="DESCRIPTION";
    public static final String MENU_DISH_IMAGE = "IMAGE";
    public static final String MENU_DISH_PRICE = "PRICE";
    public static final String MENU_DISH_FOOD_CATEGORY = "FOOD_CATEGORY";

    
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

        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(CREATE_KITCHENS_TABLE);
        sqLiteDatabase.execSQL(CREATE_LEASE_TABLE);
        sqLiteDatabase.execSQL(CREATE_MENU_DISHES_TABLE);
    }

    private void populateKitchens(){
        String[] kitchenValues= Faker.getKitchenValues();
        SQLiteDatabase db= getWritableDatabase();
        for (String value:kitchenValues) {
            String[] params = value.split(",");
            db.execSQL("INSERT INTO " + KITCHENS_TABLE_NAME +
                    " (" + KITCHEN_NAME + "," +KITCHEN_ADDRESS+","+KITCHEN_LOCALITY+ ") " + " VALUES " + "('"+params[0]+"','"+params[1]+"','"+params[2]+"')");
        }
    }

    private void populateUsers(){
        String [] usersValues = Faker.getUsersValues();
        SQLiteDatabase db= getWritableDatabase();
        ContentValues values = new ContentValues();
        for (String user : usersValues){
            String [] params = user.split(",");
            values.put(USER_FULLNAME, params[0]);
            values.put(USER_EMAIL, params[1]);
            values.put(USER_PHONE, params[2]);
            values.put(USER_ADDRESS, params[3] );
            values.put(USER_BIRTHDATE, params[4]);
            values.put(USER_PASSWORD, params[5]);
            values.put(USER_GENDER, params[6]);
            values.put(USER_TYPE, params[7]);

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
            values.put(MENU_DISH_NAME, params[0]);
            values.put(MENU_DISH_DESCRIPTION, params[1]);
            values.put(MENU_DISH_FOOD_CATEGORY, params[2]);
            values.put(MENU_DISH_VENDOR_ID, Integer.parseInt(params[3]));
            values.put(MENU_DISH_PRICE, Integer.parseInt(params[4]));
            db.insert(MENU_DISHES_TABLE_NAME, null, values);
            values.clear();
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ USERS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ KITCHENS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ LEASE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MENU_DISHES_TABLE_NAME);
        onCreate(sqLiteDatabase);
        populateKitchens();
        populateUsers();
        populateMenus();

    }

    public void initDb(){
        SQLiteDatabase db=this.getWritableDatabase();
        onUpgrade(db,1,1);
    }



}
