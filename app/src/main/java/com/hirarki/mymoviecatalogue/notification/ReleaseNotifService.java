package com.hirarki.mymoviecatalogue.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.activity.MainActivity;
import com.hirarki.mymoviecatalogue.model.Movie;
import com.hirarki.mymoviecatalogue.model.MovieList;
import com.hirarki.mymoviecatalogue.rest.ApiClient;
import com.hirarki.mymoviecatalogue.rest.ApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseNotifService extends JobService {
    private static int ID_NOTIF_RELEASE = 100;
    private final static String GROUP_KEY_MOVIE = "group_key_movie";
    List<Movie> listRelease;

    public ReleaseNotifService() {
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        getReleasedMovie(job);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listRelease.clear();
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }

    public void getReleasedMovie(final JobParameters jobParameters) {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dateToday = format.format(today);

        ApiService service = ApiClient.getClient().create(ApiService.class);

        Call<MovieList> releaseCall = service.getReleasedMovie(ApiClient.getApiKey(),
                dateToday,dateToday);

        releaseCall.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                listRelease = response.body().getResults();

                showNotif(getApplicationContext(), ID_NOTIF_RELEASE, listRelease);

                jobFinished(jobParameters, false);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                jobFinished(jobParameters, true);
                Log.e("failedNotif", "error: " + t.getMessage());
            }
        });
    }

    public void showNotif(Context context, int notifId, List<Movie> list) {
        String CHANNEL_ID = "channel_02";
        String CHANNEL_NAME = "Release Notification Channel";
        long[] vibrate = new long[]{1000, 1000, 1000, 1000, 1000};

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder;

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle()
                .addLine(list.get(0).getTitle()+" "+context.getString(R.string.release_msg)+" "+list.get(0).getVoteAverage())
                .addLine(list.get(1).getTitle()+" "+context.getString(R.string.release_msg)+" "+list.get(1).getVoteAverage())
                .addLine(list.get(2).getTitle()+" "+context.getString(R.string.release_msg)+" "+list.get(2).getVoteAverage())
                .setBigContentTitle(context.getString(R.string.notif_release))
                .setSummaryText(context.getString(R.string.app_name));

        builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setContentTitle("3 " + context.getString(R.string.notif_release))
                .setContentText(context.getString(R.string.daily_notif))
                .setContentIntent(pendingIntent)
                .setGroup(GROUP_KEY_MOVIE)
                .setGroupSummary(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setStyle(style)
                .setSound(sound)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(vibrate);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(vibrate);

            builder.setChannelId(CHANNEL_ID);

            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (manager != null) {
            manager.notify(notifId, notification);
        }
    }
}
