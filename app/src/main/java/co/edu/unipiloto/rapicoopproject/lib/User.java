package co.edu.unipiloto.rapicoopproject.lib;

import androidx.annotation.NonNull;

import java.io.Serializable;

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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
