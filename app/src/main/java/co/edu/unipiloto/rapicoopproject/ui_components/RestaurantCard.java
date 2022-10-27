package co.edu.unipiloto.rapicoopproject.ui_components;

public class RestaurantCard {
    String restaurantName;
    String restaurantLocality;
    String restaurantAddress;
    int restaurantLogo;
    int background;

    public RestaurantCard(String restaurantName, String restaurantLocality, int restaurantLogo, int background) {
        this.restaurantName = restaurantName;
        this.restaurantLocality = restaurantLocality;
        this.restaurantAddress = restaurantAddress;
        this.restaurantLogo = restaurantLogo;
        this.background = background;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantLocality() {
        return restaurantLocality;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public int getRestaurantLogo() {
        return restaurantLogo;
    }

    public int getBackground() {
        return background;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setRestaurantLocality(String restaurantLocality) {
        this.restaurantLocality = restaurantLocality;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public void setRestaurantLogo(int restaurantLogo) {
        this.restaurantLogo = restaurantLogo;
    }

    public void setBackground(int background) {
        this.background = background;
    }
}
