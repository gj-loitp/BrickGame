package com.brick

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import com.brick.Values.CHANNEL_NAME
import com.brick.Values.SCORE_CHANNEL
import com.brick.utils.NotificationUtil
import com.brick.utils.setupApplovinAd

//TODO gesture up/down
//TODO music bkg
//TODO vibrate
//done
//ic_launcher https://easyappicon.com/
//leak canary
//applovin
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        this.setupApplovinAd()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = getSystemService(NOTIFICATION_SERVICE) as? NotificationManager?
            if (nm != null) {
                NotificationUtil.createChannel(
                    /* nm = */ nm,
                    /* id = */ SCORE_CHANNEL,
                    /* channelName = */ CHANNEL_NAME,
                    /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
                )
            }
        }
    }
}
