package co.edu.unipiloto.rapicoopproject.entities;

import android.content.Context;
import android.view.contentcapture.ContentCaptureCondition;

import java.util.List;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;
import co.edu.unipiloto.rapicoopproject.interfaces.IUserFacade;
import co.edu.unipiloto.rapicoopproject.lib.User;

public class UserFacade extends AbstractFacade implements IUserFacade {

    private RapicoopDataBaseHelper dataBaseHelper;

    @Override
    protected RapicoopDataBaseHelper getDatabaseHelper(Context context) {
        dataBaseHelper = RapicoopDataBaseHelper.getInstance(context);
        return dataBaseHelper;
    }

    @Override
    public long insertUser(User user) {
       return 0;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }


}
