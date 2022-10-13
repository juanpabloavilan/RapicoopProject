package co.edu.unipiloto.rapicoopproject.lib;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import co.edu.unipiloto.rapicoopproject.db.RapicoopDataBaseHelper;

public class LeaseManager {
    private static final float MAX_LEASE = 12f;
    private static final float MIN_LEASE = 1f;
    private final RapicoopDataBaseHelper db;
    private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", new Locale("es","CO"));

    public LeaseManager(Context managerContext) {
        db = RapicoopDataBaseHelper.getInstance(managerContext);
    }

    public KitchenLease newLease(int vendorId, int kitchenId, String ini_date, String end_date) throws ParseException {
        KitchenLease newLease = new KitchenLease(vendorId, kitchenId, ini_date, end_date);
        if (!validLease(newLease)){
            return null;
        }
        db.insertLease(newLease);
        return newLease;
    }

    private boolean validLease(KitchenLease lease) throws ParseException {
        Calendar start = toCalendar(format.parse(lease.getIniDate()));
        Calendar end = toCalendar(format.parse(lease.getEndDate()));
        float diffMonths = Duration.between(start.toInstant(),end.toInstant()).toDays() / 30f;
        boolean validPeriod = (diffMonths >= MIN_LEASE && diffMonths <= MAX_LEASE);
        return db.leaseAvailability(lease.getKitchenId(),lease.getVendorId()) && validPeriod;
    }

    private Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
