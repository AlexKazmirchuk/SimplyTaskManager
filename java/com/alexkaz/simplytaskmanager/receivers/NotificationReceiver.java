package com.alexkaz.simplytaskmanager.receivers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import com.alexkaz.simplytaskmanager.MainActivity;
import com.alexkaz.simplytaskmanager.R;
import java.util.Calendar;
import java.util.Date;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String BOOT_COMPLETED_ACTION = "android.intent.action.BOOT_COMPLETED";
    private static final String NOTIFICATION_ACTION = "com.alexkaz.watching.tasks";
    private static final int HOUR_OF_START = 7;
    private static final int MINUTE_OF_START = 30;
    private static final int NOTIFICATION_ID = 1843;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == null || intent.getAction().equals(BOOT_COMPLETED_ACTION)){
            initRepeatAlarm(context);
        }
        if (intent.getAction() != null){
            if(intent.getAction().equals(NOTIFICATION_ACTION)){
                showNotification(context);
            }
        }
    }

    private void initRepeatAlarm(Context context){
        Intent myIntent = new Intent(context, NotificationReceiver.class);
        myIntent.setAction(NOTIFICATION_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC
                ,getTime()
                ,AlarmManager.INTERVAL_DAY
                ,pendingIntent);
    }

    private long getTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)
                , HOUR_OF_START, MINUTE_OF_START);
        Date date = calendar.getTime();
        return date.getTime();
    }

    private void showNotification(Context context){
        Intent notifIntent = new Intent(context, MainActivity.class);
        notifIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,notifIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder notifBuilder = new Notification.Builder(context);
        notifBuilder.setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_app)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_app))
                .setTicker(context.getString(R.string.notification_ticker_text))
                .setContentTitle(context.getString(R.string.notification_content_title))
                .setContentText(context.getString(R.string.notification_content_text));
        NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.BigTextStyle(notifBuilder).bigText(context.getString(R.string.notification_content_text)).build();
        notifManager.notify(NOTIFICATION_ID,notification);
    }

}
