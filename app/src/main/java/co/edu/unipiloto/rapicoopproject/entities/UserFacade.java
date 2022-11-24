package co.edu.unipiloto.rapicoopproject.entities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.contentcapture.ContentCaptureCondition;

import java.util.List;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;
import co.edu.unipiloto.rapicoopproject.interfaces.IUserFacade;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class UserFacade extends AbstractFacade implements IUserFacade {
    private final String TAG = "USER_FACADE";
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

    private Context context;

    private static UserFacade instance;


    public static UserFacade getInstance(Context context){
        if(instance == null){
            instance = new UserFacade();
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

    @Override
    public long insertUser(User user) {
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        //getDatabaseHelper(instance.context).initDb();
        ContentValues userDataSet = new ContentValues();
        userDataSet.put(USER_FULLNAME,user.getFullName());
        userDataSet.put(USER_EMAIL,user.getEmail());
        userDataSet.put(USER_PHONE,user.getPhone());
        userDataSet.put(USER_ADDRESS,user.getAddress());
        userDataSet.put(USER_BIRTHDATE,user.getBirthdate());
        userDataSet.put(USER_PASSWORD,user.getPassword());
        userDataSet.put(USER_GENDER,user.getGender());
        userDataSet.put(USER_TYPE,user.getType());

        return db.insert(USERS_TABLE_NAME,null,userDataSet);
    }

    @Override
    public User getUserByEmail(String emailInput) {
        String USER_SELECT_QUERY = "SELECT * FROM "+ USERS_TABLE_NAME + " " +
                "WHERE "+ USER_EMAIL +" = "+"'"+ emailInput+"'";
        User user = null;
        SQLiteDatabase db = getDatabaseHelper(instance.context).getReadableDatabase();
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

    @Override
    public User updateUser(User user) {
        SQLiteDatabase db = RapicoopDataBaseHelper.getInstance(instance.context).getWritableDatabase();
        ContentValues userData = new ContentValues();
        userData.put(USER_FULLNAME,user.getFullName());
        userData.put(USER_EMAIL,user.getEmail());
        userData.put(USER_PHONE,user.getPhone());
        userData.put(USER_PASSWORD,user.getPassword());
        userData.put(USER_GENDER,user.getGender());
        userData.put(USER_TYPE,user.getType());

        try{
         db.update(USERS_TABLE_NAME, userData, USER_ID + " = " + user.getId(), null );
         return getUserById(user.getId());
        }catch (Exception e){
            Log.e(TAG, "Error al actualizar el usuario: " + user.toString());
            return null;
        }
    }

    @Override
    public User getUserById(int idInput) {
        String USER_SELECT_QUERY = "SELECT * FROM "+ USERS_TABLE_NAME + " " +
                "WHERE "+ USER_ID +" = "+ idInput;
        System.out.println(USER_SELECT_QUERY);
        User user = null;
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
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

    public double[] getUserCoordinates(int clientId,Context deliverContext){
        String USER_SELECT_QUERY = "SELECT * FROM "+ USERS_TABLE_NAME +
                " WHERE "+ USER_ID +" = "+ clientId;
        System.out.println(USER_SELECT_QUERY);
        SQLiteDatabase db = getDatabaseHelper(instance.context).getWritableDatabase();
        Cursor cursor = db.rawQuery(USER_SELECT_QUERY, null);
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
            cursor.close();
            User user = new User(id, fullname, email, phone, address, birthdate, password, gender, type);
            return user.getAddressCoordinates(deliverContext);
        }
        cursor.close();
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }


}
