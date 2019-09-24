package com.hirarki.mymoviecatalogue.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.hirarki.mymoviecatalogue.R;

import java.util.Calendar;

public class ReleaseNotification extends BroadcastReceiver {
    private static int ID_NOTIF_RELEASE = 100;
    FirebaseJobDispatcher dispatcher;
    private String TAG_DISPATCHER = "mydispatcher";

    public ReleaseNotification() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        startDispatch();
    }

    private void startDispatch() {
        Job job = dispatcher.newJobBuilder()
                .setService(ReleaseNotifService.class)
                .setTag(TAG_DISPATCHER)
                .setRecurring(false)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setTrigger(Trigger.executionWindow(0, 1))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();

        dispatcher.mustSchedule(job);
    }

    private void stopDispatch() {
        dispatcher.cancel(TAG_DISPATCHER);
    }

    public void setTimeNotif(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, ReleaseNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NOTIF_RELEASE, intent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, context.getString(R.string.release_setting), Toast.LENGTH_SHORT).show();
    }

    public void disableReleaseNotif(Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NOTIF_RELEASE, intent, 0);

        manager.cancel(pendingIntent);

        Toast.makeText(context, context.getString(R.string.disable_release), Toast.LENGTH_SHORT).show();
    }
}
