package co.edu.unipiloto.rapicoopproject.entities;

import android.content.Context;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;

public abstract class AbstractFacade {
    protected abstract RapicoopDataBaseHelper getDatabaseHelper(Context context);
}
