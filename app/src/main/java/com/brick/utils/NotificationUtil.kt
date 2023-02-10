package com.brick.utils

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.brick.R
import com.brick.Values.NOTIFICATION_ID
import com.brick.Values.SCORE_CHANNEL
import com.brick.ui.score.ScoreActivity

class NotificationUtil(
    private val context: Context, private val score: Int
) {
    companion object {
        fun createChannel(
            nm: NotificationManager, id: String?, channelName: String?, importance: Int
        ) {
            var channel: NotificationChannel?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = nm.getNotificationChannel(id)
                if (channel == null) {
                    channel = NotificationChannel(
                        /* id = */ id,
                        /* name = */ channelName,
                        /* importance = */ importance
                    )
                    channel.setSound(/* sound = */ null, /* audioAttributes = */ null)
                    nm.createNotificationChannel(channel)
                }
            }
        }
    }

    private var pendingIntent: PendingIntent? = null

    init {
        initIntent(context)
    }

    private fun initIntent(context: Context) {
        val intent = Intent(context, ScoreActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
    }

    private val notificationBuilder: Notification
        get() {
            val builder: NotificationCompat.Builder =
                NotificationCompat.Builder(context, SCORE_CHANNEL).setSmallIcon(R.drawable.ic_star)
                    .setContentTitle(context.getString(R.string.new_score_notification_title))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent).setContentText(
                        context.getString(R.string.congrats_sub_title) + " - " + score + "!"
                    ).setColorized(true).setAutoCancel(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                builder.color = context.getColor(R.color.colorPrimary)
            }
            return builder.build()
        }

    fun createNotification() {
        val notificationManager = NotificationManagerCompat.from(
            context
        )
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder)
    }

}
