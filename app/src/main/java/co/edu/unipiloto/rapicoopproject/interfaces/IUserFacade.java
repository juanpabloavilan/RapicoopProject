package co.edu.unipiloto.rapicoopproject.interfaces;

import java.util.List;

import co.edu.unipiloto.rapicoopproject.lib.User;

public interface IUserFacade {
    long insertUser(User user);
    User getUserByEmail(String email);
    User updateUser(User user);
    User getUserById(int id);
    List<User> getAllUsers();
}
