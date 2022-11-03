package co.edu.unipiloto.rapicoopproject.interfaces;

import co.edu.unipiloto.rapicoopproject.lib.Restaurant;

public interface IRestaurantFacade {
    long insertRestaurant(Restaurant restaurant);
    boolean hasRestaurant(int possibleOwnerId);
}
