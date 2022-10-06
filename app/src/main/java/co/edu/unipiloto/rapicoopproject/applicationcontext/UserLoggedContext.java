package co.edu.unipiloto.rapicoopproject.applicationcontext;

import java.io.Serializable;

import co.edu.unipiloto.rapicoopproject.lib.User;

public class UserLoggedContext implements Serializable {
    private static UserLoggedContext instance;
    private User user;

    private UserLoggedContext(){
    }

    public static synchronized UserLoggedContext getInstance(){
        if(instance == null){
            instance = new UserLoggedContext();
        }
        return instance;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

}
