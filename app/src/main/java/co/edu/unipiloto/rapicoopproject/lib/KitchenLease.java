package co.edu.unipiloto.rapicoopproject.lib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class KitchenLease {
    private int id;
    private int vendorId;
    private int kitchenId;
    private String iniDate;
    private String endDate;

    public KitchenLease(int vendor_id, int kitchenId, String iniDate, String endDate) {
        this.vendorId = vendor_id;
        this.kitchenId = kitchenId;
        this.iniDate = iniDate;
        this.endDate = endDate;
    }

    public KitchenLease(int id, int vendor_id, int kitchenId, String iniDate, String endDate) {
        this.id = id;
        this.vendorId = vendor_id;
        this.kitchenId = kitchenId;
        this.iniDate = iniDate;
        this.endDate = endDate;
    }

    public int getId(){
        return id;
    }

    public int getVendorId() {
        return vendorId;
    }

    public int getKitchenId() {
        return kitchenId;
    }

    public String getIniDate() { return iniDate; }

    public String getEndDate() {
        return endDate;
    }
}
