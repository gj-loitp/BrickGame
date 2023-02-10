package com.brick.utils;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.brick.R;
import com.brick.ui.score.ScoreActivity;

import static com.brick.Values.NOTIFICATION_ID;
import static com.brick.Values.SCORE_CHANNEL;

public class NotificationUtil {

    private final Context context;
    private PendingIntent pendingIntent;
    private final int score;

    public NotificationUtil(Context context, int newScore) {
        this.context = context;
        this.score = newScore;
        initIntent(context);
    }

    private void initIntent(Context context) {
        Intent intent = new Intent(context, ScoreActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
    }

    private Notification getNotificationBuilder() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, SCORE_CHANNEL)
                .setSmallIcon(R.drawable.ic_star)
                .setContentTitle(context.getString(R.string.new_score_notification_title))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setContentText(context.getString(R.string.congrats_sub_title)
                        + " - " + score + "!")
                .setColorized(true)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.setColor(context.getColor(R.color.colorPrimary));
        }
        return builder.build();
    }

    public void createNotification() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, getNotificationBuilder());
    }

    public static void createChannel(NotificationManager nm, String id, String channelName, int importance) {
        NotificationChannel channel;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = nm.getNotificationChannel(id);
            if (channel == null) {
                channel = new NotificationChannel(id, channelName, importance);
                channel.setSound(null, null);
                nm.createNotificationChannel(channel);
            }
        }
    }
}
