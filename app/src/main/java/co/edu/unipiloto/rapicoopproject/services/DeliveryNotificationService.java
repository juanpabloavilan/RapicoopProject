package co.edu.unipiloto.rapicoopproject.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DeliveryNotificationService extends IntentService {

    // TODO: Rename parameters
    public static final String EXTRA_STATUS = "EXTRA_STATUS";

    public DeliveryNotificationService() {
        super("DeliveryNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        assert intent != null;
        String deliveryStatus = intent.getStringExtra(EXTRA_STATUS);
        notifyUsers(deliveryStatus);
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void notifyUsers(final String status) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle("ORDEN " + status + "!")
                .setContentText("Su orden ha sido " + status)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
    }
}