package com.hirarki.mymoviecatalogue.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.widget.Toast;

import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.activity.MainActivity;

import java.util.Calendar;

public class DailyNotification extends BroadcastReceiver {
    private static final int ID_NOTIF = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        showDailyNotif(context,
                context.getString(R.string.app_name),
                context.getString(R.string.daily_notif),
                ID_NOTIF);
    }

    public void showDailyNotif(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "channel_01";
        String CHANNEL_NAME = "Daily Notification Channel";
        long[] vibrate = new long[]{1000, 1000, 1000, 1000, 1000};

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(sound)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(vibrate);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(vibrate);

            builder.setChannelId(CHANNEL_ID);
            manager.createNotificationChannel(channel);
        }

        Notification notification = builder.build();
        manager.notify(notifId, notification);
    }

    public void setRepeatNotif(Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NOTIF, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (manager != null) {
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }

        Toast.makeText(context, context.getString(R.string.daily_setting), Toast.LENGTH_SHORT).show();
    }

    public void disableNotif(Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NOTIF, intent, 0);

        if (manager != null) {
            manager.cancel(pendingIntent);
        }

        Toast.makeText(context, context.getString(R.string.disable_daily), Toast.LENGTH_SHORT).show();
    }
}
