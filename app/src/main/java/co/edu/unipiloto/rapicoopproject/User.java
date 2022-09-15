package co.edu.unipiloto.rapicoopproject;

public class User {
    private String fullName;
    private String email;
    private String cellphone;
    private String password;
    private String gender;
    private String type;

    public User(String fullName, String email, String cellphone, String password, String type, String gender){
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
}
