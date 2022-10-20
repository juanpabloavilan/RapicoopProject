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
                "VENDOR_ID	INTEGER NOT NULL UNIQUE," +
                "KITCHEN_ID	INTEGER NOT NULL UNIQUE," +
                "INI_DATE TEXT NOT NULL," +
                "END_DATE TEXT NOT NULL," +
                "FOREIGN KEY(VENDOR_ID) REFERENCES "+USERS_TABLE_NAME+"(id)," +
                "FOREIGN KEY(KITCHEN_ID) REFERENCES "+KITCHENS_TABLE_NAME+"(id))";

        String CREATE_MENU_DISHES_TABLE = "CREATE TABLE " + MENU_DISHES_TABLE_NAME + "("+
                MENU_DISH_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                MENU_DISH_NAME + " TEXT NOT NULL," +
                MENU_DISH_DESCRIPTION+" TEXT NOT NULL," +
                MENU_DISH_FOOD_CATEGORY+" TEXT NOT NULL," +
                MENU_DISH_IMAGE + " BLOB," +
                "FOREIGN KEY ("+MENU_DISH_VENDOR_ID + ") REFERENCES "+ USERS_TABLE_NAME +"("+USER_ID+"))";

        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(CREATE_KITCHENS_TABLE);
        sqLiteDatabase.execSQL(CREATE_LEASE_TABLE);
        //sqLiteDatabase.execSQL(CREATE_MENU_DISHES_TABLE);
    }

    public void populateKitchens(){
        String[] kitchenValues = {"Plaza Industrial,Calle 132-45C,Castilla","Villa Mendoza,Carrera 30,Castilla","Plazoleta Verde,Carrera 71A-10B,Chapinero",
                "Centro Insular,CL 32 # 6B - 43,Usaquen","C.C. Abelidez,AK 7 # 74 - 26,Usaquen"};
        SQLiteDatabase db= getWritableDatabase();
        for (String value:kitchenValues) {
            String[] params = value.split(",");
            db.execSQL("INSERT INTO " + KITCHENS_TABLE_NAME +
                    " (" + KITCHEN_NAME + "," +KITCHEN_ADDRESS+","+KITCHEN_LOCALITY+ ") " + " VALUES " + "('"+params[0]+"','"+params[1]+"','"+params[2]+"')");
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
    }

    public void initDb(){
        SQLiteDatabase db=this.getWritableDatabase();
        onUpgrade(db,1,1);
    }

    public Cursor getUserData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor userData = db.rawQuery("SELECT * FROM "+ USERS_TABLE_NAME +"WHERE id="+id+"",null);
        return userData;
    }

    public long insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userDataSet = new ContentValues();
        userDataSet.put(USER_FULLNAME,user.getFullName());
        userDataSet.put(USER_EMAIL,user.getEmail());
        userDataSet.put(USER_PHONE,user.getPhone());
        userDataSet.put(USER_ADDRESS,user.getAddress());
        userDataSet.put(USER_BIRTHDATE,user.getBirthdate());
        userDataSet.put(USER_PASSWORD,user.getPassword());
        userDataSet.put(USER_GENDER,user.getGender());
        userDataSet.put(USER_TYPE,user.getType());

        long ins_result = db.insert(USERS_TABLE_NAME,null,userDataSet);
        return ins_result;
    }

    public Cursor getAllUserData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor allUsers = db.rawQuery("SELECT * FROM "+ USERS_TABLE_NAME,null);
        return allUsers;
    }


    public User getUserByEmail(String emailInput) {
        String USER_SELECT_QUERY = "SELECT * FROM "+ USERS_TABLE_NAME + " " +
                                    "WHERE "+ USER_EMAIL +" = "+"'"+ emailInput+"'";
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_SELECT_QUERY, null);
        try{
            if(cursor.moveToFirst()){

                @SuppressLint("Range") String fullname = cursor.getString(cursor.getColumnIndex(USER_FULLNAME));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(USER_GENDER));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
                @SuppressLint("Range") String birthdate = cursor.getString(cursor.getColumnIndex(USER_BIRTHDATE));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(USER_ADDRESS));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(USER_PHONE));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(USER_TYPE));
                @SuppressLint("Range") int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(USER_ID)));
                user = new User(id, fullname, email, phone, address, birthdate, password, gender, type);
            }
        }catch (Exception e){
            Log.d(TAG, "Error al buscar por email");
        }finally {
            if (cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return user;
    }

    /*public User updateUser(String emailInput, User user){
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(USER_ID, user.getId());
            values
        }
        String USER_UPDATE_QUERY = "SELECT"
    }*/
    public String[] getAllKitchenLocalities(){
        String LOCALITY_SELECT_QUERY = "SELECT DISTINCT " + KITCHEN_LOCALITY + " FROM " + KITCHENS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor localitiesCursor = db.rawQuery(LOCALITY_SELECT_QUERY,null);
        ArrayList<String> localities = new ArrayList<String>();
        while(localitiesCursor.moveToNext()) {
            @SuppressLint("Range") String locality = localitiesCursor.getString(localitiesCursor.getColumnIndex(KITCHEN_LOCALITY));
            localities.add(locality);
        }
        localitiesCursor.close();
        return localities.toArray(new String[0]);
    }

    public Kitchen[] getAllKitchensByLocality(String targetLocality){
        String KITCHEN_SELECT_QUERY = "SELECT * FROM " + KITCHENS_TABLE_NAME +
                " WHERE " + KITCHEN_LOCALITY + " = '" + targetLocality + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor kitchensCursor = db.rawQuery(KITCHEN_SELECT_QUERY,null);
        ArrayList<Kitchen> kitchens = new ArrayList<Kitchen>();
        while(kitchensCursor.moveToNext()){
            @SuppressLint("Range") int id = Integer.parseInt(kitchensCursor.getString(kitchensCursor.getColumnIndex(KITCHEN_ID)));
            @SuppressLint("Range") String name = kitchensCursor.getString(kitchensCursor.getColumnIndex(KITCHEN_NAME));
            @SuppressLint("Range") String address = kitchensCursor.getString(kitchensCursor.getColumnIndex(KITCHEN_ADDRESS));
            @SuppressLint("Range") String locality = kitchensCursor.getString(kitchensCursor.getColumnIndex(KITCHEN_LOCALITY));
            kitchens.add(new Kitchen(id, name, address, locality));
        }
        kitchensCursor.close();
        return kitchens.toArray(new Kitchen[0]);
    }

    public long insertLease(KitchenLease lease){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues leaseDataSet = new ContentValues();
        leaseDataSet.put(LEASE_VENDOR_ID,lease.getVendorId());
        leaseDataSet.put(LEASE_KITCHEN_ID,lease.getKitchenId());
        leaseDataSet.put(LEASE_INI_DATE,lease.getIniDate());
        leaseDataSet.put(LEASE_END_DATE,lease.getEndDate());

        return db.insert(LEASE_TABLE_NAME,null,leaseDataSet);
    }

    public boolean leaseAvailability(int kitchenId, int vendorId){
        String LEASE_SELECT_QUERY = "SELECT * FROM "+ LEASE_TABLE_NAME +
                " WHERE "+ LEASE_KITCHEN_ID +" = "+ kitchenId +
                " OR " + LEASE_VENDOR_ID +" = "+ vendorId;
        SQLiteDatabase db = getReadableDatabase();
        Cursor leaseMatch = db.rawQuery(LEASE_SELECT_QUERY,null);
        if(leaseMatch.moveToFirst()){ //O una cocina ya esta arrendada o un vendedor ya tiene cocina
            return false;
        }
        leaseMatch.close();
        return true;
    }


}
