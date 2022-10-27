package co.edu.unipiloto.rapicoopproject.interfaces;

import co.edu.unipiloto.rapicoopproject.lib.Kitchen;
import co.edu.unipiloto.rapicoopproject.lib.KitchenLease;

public interface ILeaseFacade {
    String[] getAllKitchenLocalities();
    Kitchen[] getAllKitchensByLocality(String targetLocality);
    long insertLease(KitchenLease lease);
    boolean leaseAvailability(int kitchenId, int vendorId);


}
