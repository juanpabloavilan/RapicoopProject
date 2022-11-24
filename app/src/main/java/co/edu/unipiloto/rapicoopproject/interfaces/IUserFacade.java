package co.edu.unipiloto.rapicoopproject.interfaces;

import android.content.Context;

import java.util.List;

import co.edu.unipiloto.rapicoopproject.lib.User;

public interface IUserFacade {
    long insertUser(User user);
    User getUserByEmail(String email);
    User updateUser(User user);
    User getUserById(int id);
    double[] getUserCoordinates(int clientId,Context context);
    List<User> getAllUsers();
}
