package co.edu.unipiloto.rapicoopproject.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import co.edu.unipiloto.rapicoopproject.User;

public class RapicoopDataBaseHelper extends SQLiteOpenHelper {

    private  static  RapicoopDataBaseHelper instance;

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
    public static final String USER_PASSWORD ="PASSWORD";
    public static final String USER_GENDER ="GENDER";
    public static final String USER_TYPE ="TYPE";

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
                "PASSWORD TEXT NOT NULL," +
                "GENDER TEXT NOT NULL," +
                "TYPE TEXT NOT NULL)";

        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ USERS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void initDb(){
        SQLiteDatabase db=this.getWritableDatabase();
        onUpgrade(db,1,1);
    }

    public boolean insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userDataSet = new ContentValues();
        userDataSet.put(USER_FULLNAME,user.getFullName());
        userDataSet.put(USER_EMAIL,user.getEmail());
        userDataSet.put(USER_PHONE,user.getPhone());
        userDataSet.put(USER_PASSWORD,user.getPassword());
        userDataSet.put(USER_GENDER,user.getGender());
        userDataSet.put(USER_TYPE,user.getType());

        long ins_result = db.insert(USERS_TABLE_NAME,null,userDataSet);
        return ins_result != -1;
    }

    public Cursor getUserData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor userData = db.rawQuery("SELECT * FROM "+ USERS_TABLE_NAME +"WHERE id="+id+"",null);
        return userData;
    }

    public Cursor getAllUserData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor allUsers = db.rawQuery("SELECT * FROM "+ USERS_TABLE_NAME,null);
        return allUsers;
    }


    public User getUserByEmail(String emailInput) {
        String USER_SELECT_QUERY = "SELECT * FROM "+ USERS_TABLE_NAME + " " +
                                    "WHERE "+ USER_EMAIL +" = "+"'"+ emailInput+"'";
        System.out.println(USER_SELECT_QUERY);
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_SELECT_QUERY, null);
        try{
            if(cursor.moveToFirst()){

                @SuppressLint("Range") String fullname = cursor.getString(cursor.getColumnIndex(USER_FULLNAME));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(USER_GENDER));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(USER_PHONE));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(USER_TYPE));
                user = new User(fullname, email, password, password, type, gender);

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
}
