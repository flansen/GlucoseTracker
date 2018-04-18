package de.flansen.glucosetracker.overview.home.impl;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import de.flansen.glucosetracker.AlarmReceiver;
import de.flansen.glucosetracker.R;
import de.flansen.glucosetracker.common.Constants;
import de.flansen.glucosetracker.overview.OverviewActivity;
import de.flansen.glucosetracker.overview.home.AlarmHandler;

/**
 * Created by Florian on 28.10.2016.
 */

public class AlarmHandlerImpl implements AlarmHandler {
    private static final int REQUEST = 1242;
    private AlarmManager alarmManager;
    private Context context;

    public AlarmHandlerImpl(Context context, AlarmManager alarmManager) {
        this.context = context;
        this.alarmManager = alarmManager;
    }

    @Override
    public void setAlarm(long msUntilAlarm, boolean isEnabled) {
        Intent intent = new Intent(Constants.ACTION_ALARM_INTENT);
        intent.putExtra(AlarmReceiver.NOTIFICATION_EXTRA, getNotification());
        intent.putExtra(AlarmReceiver.NOTIFICATION_ID_EXTRA, 1);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, REQUEST, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (!isEnabled) {
            alarmManager.cancel(pIntent);
            return;
        }
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + msUntilAlarm, pIntent);
    }

    private Notification getNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent i = new Intent(context, OverviewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
        Notification notification = builder
                .setContentTitle(Constants.NOTIFICATION_TITLE)
                .setContentIntent(pendingIntent)
                .setContentText(Constants.NOTIFICATION_TEXT)
                .setSmallIcon(R.drawable.ic_blood)
                .setAutoCancel(true)
                .build();
        notification.defaults |= Notification.DEFAULT_ALL;
        return notification;
    }
}
