package de.fha.bwi50101;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import de.fha.bwi50101.common.Constants;

/**
 * Created by Florian on 28.10.2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_EXTRA = "notification";
    public static final String NOTIFICATION_ID_EXTRA = "notification_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION_EXTRA);
        int id = intent.getIntExtra(NOTIFICATION_ID_EXTRA, 0);
        notificationManager.notify(id, notification);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(Constants.ALARM_ENABLED_KEY, false).apply();
    }
}
