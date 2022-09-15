package co.edu.unipiloto.rapicoopproject;

public class User {
    private String fullName;
    private String username;
    private String email;
    private String password;
    private String gender;
    private String type;

    public User(String fullName, String username, String email, String password, String gender, String type){
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
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

    public String getType() {
        return type;
    }
}
