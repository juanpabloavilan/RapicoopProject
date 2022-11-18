package co.edu.unipiloto.rapicoopproject.services;

import static co.edu.unipiloto.rapicoopproject.CurrentDeliveryActivity.DELIVERY_NOTIFICATIONS_CHANNEL;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import co.edu.unipiloto.rapicoopproject.CurrentDeliveryActivity;
import co.edu.unipiloto.rapicoopproject.R;

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
    public static final int NOTIFICATION_ID = 5453;

    public DeliveryNotificationService() {
        super("DeliveryNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("sdsa","Im here");
        assert intent != null;
        String deliveryStatus = intent.getStringExtra(EXTRA_STATUS);
        notifyUsers(deliveryStatus);
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void notifyUsers(final String statusMessage) {
        Intent notificationIntent = new Intent(DeliveryNotificationService.this, CurrentDeliveryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                notificationIntent,
                0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, DELIVERY_NOTIFICATIONS_CHANNEL)
                .setContentTitle("Actualizacion de domicilio")
                .setContentText(statusMessage)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.delivery_man)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[] {0,1000});

        NotificationChannel serviceChannel = new NotificationChannel(
                DELIVERY_NOTIFICATIONS_CHANNEL,
                "Delivery notifications channel",
                NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(serviceChannel);
        manager.notify(NOTIFICATION_ID, notification.build());
    }
}