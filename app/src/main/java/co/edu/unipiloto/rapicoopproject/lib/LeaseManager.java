package co.edu.unipiloto.rapicoopproject.lib;

import android.content.Context;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;

public class LeaseManager {
    private static final int MAX_LEASE = 12;
    private static final int MIN_LEASE = 1;
    private final RapicoopDataBaseHelper db;

    public LeaseManager(Context managerContext) {
        db = RapicoopDataBaseHelper.getInstance(managerContext);
    }

    public KitchenLease newLease(int id,int vendorId,int kitchenId, int ini_date, int end_date){
        if (!validLease(vendorId,kitchenId,ini_date,end_date)){
            return null;
        }
        KitchenLease newLease = new KitchenLease(id,vendorId, kitchenId, ini_date, end_date);
        db.insertLease(newLease);
        return newLease;
    }

    private boolean validLease(int vendorId, int kitchenId, int ini_date, int end_date){
        int leaseMonths = ini_date - end_date;
        boolean validPeriod = (leaseMonths >= MIN_LEASE && leaseMonths <= MAX_LEASE);
        return db.leaseAvailability(kitchenId,vendorId) && validPeriod;
    }

}
