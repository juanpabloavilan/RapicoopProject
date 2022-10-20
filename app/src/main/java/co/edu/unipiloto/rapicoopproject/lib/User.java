package co.edu.unipiloto.rapicoopproject.lib;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String fullName;
    private String email;
    private String cellphone;
    private String password;
    private String gender;
    private String type;

    public User(int id, String fullName, String email, String cellphone, String password, String type, String gender){
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.cellphone = cellphone;
        this.password = password;
        this.gender = gender;
        this.type = type;
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
                '}';
    }
}
