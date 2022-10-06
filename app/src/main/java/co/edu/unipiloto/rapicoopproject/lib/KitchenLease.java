package co.edu.unipiloto.rapicoopproject.lib;

public class KitchenLease {
    private int id;
    private int vendor_id;
    private int kitchen_id;
    private int ini_date;
    private int end_date;

    public KitchenLease(int id,int vendor_id, int kitchen_id, int ini_date, int end_date) {
        this.id = id;
        this.vendor_id = vendor_id;
        this.kitchen_id = kitchen_id;
        this.ini_date = ini_date;
        this.end_date = end_date;
    }

    public int getId(){
        return id;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    public int getKitchen_id() {
        return kitchen_id;
    }

    public void setKitchen_id(int kitchen_id) {
        this.kitchen_id = kitchen_id;
    }

    public int getIni_date() {
        return ini_date;
    }

    public void setIni_date(int ini_date) {
        this.ini_date = ini_date;
    }

    public int getEnd_date() {
        return end_date;
    }

    public void setEnd_date(int end_date) {
        this.end_date = end_date;
    }
}
