package co.edu.unipiloto.rapicoopproject.lib;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Kitchen implements Serializable {
    private int id;
    private String name;
    private String address;
    private String locality;

    public Kitchen(int id, String name, String address, String locality) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.locality = locality;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLocality() { return locality; }

    @NonNull
    @Override
    public String toString() {
        return  name + " en " + address;
    }
}
