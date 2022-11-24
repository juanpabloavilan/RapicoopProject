package co.edu.unipiloto.rapicoopproject.lib;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private int id;
    private String fullName;
    private String email;
    private String cellphone;
    private String password;
    private String gender;
    private String type;
    private String birthdate;
    private String address;

    public User(String fullName, String email, String cellphone, String address, String birthdate, String password, String gender, String type) {
        this.fullName = fullName;
        this.email = email;
        this.cellphone = cellphone;
        this.password = password;
        this.gender = gender;
        this.type = type;
        this.birthdate = birthdate;
        this.address = address;
    }

    public User(int id, String fullName, String email, String cellphone, String address, String birthdate, String password, String gender, String type) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.cellphone = cellphone;
        this.password = password;
        this.gender = gender;
        this.type = type;
        this.birthdate = birthdate;
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() { return cellphone; }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getAddress() {
        return address;
    }

    public double[] getAddressCoordinates(Context context) {
        double[] coordinates = {0.0, 0.0};
        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList;
        try{
            addressList = geocoder.getFromLocationName(address, 5);
            if(!addressList.isEmpty()){
                coordinates[0] = addressList.get(0).getLatitude();
                coordinates[1] = addressList.get(0).getLongitude();
            }
        } catch(Exception err) {
            err.printStackTrace();
        }
        return coordinates;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", type='" + type + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
