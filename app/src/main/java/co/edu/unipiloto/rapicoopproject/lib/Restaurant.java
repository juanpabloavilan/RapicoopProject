package co.edu.unipiloto.rapicoopproject.lib;

import java.util.ArrayList;

import co.edu.unipiloto.rapicoopproject.R;

public class Restaurant {
    private int id;
    private String name;
    private String type;
    private int backgroundImg = R.drawable.default_background_glass;
    private int logoImg = R.drawable.restaurant_logo;
    final private int ownerId;

    public Restaurant(String name, String type, int ownerId){
        this.name = name;
        this.type = type;
        this.ownerId = ownerId;
    }

    public Restaurant(int id, String name, String type, int ownerId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(int backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public int getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(int logoImg) {
        this.logoImg = logoImg;
    }
}
