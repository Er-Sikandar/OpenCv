package `in`.droom.target37

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class RunningApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel= NotificationChannel("running_channel","Running Notifications",
                NotificationManager.IMPORTANCE_HIGH)
            val notyManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notyManager.createNotificationChannel(channel)
            /**
             * For Backgroound Service
             */
            val channelBack= NotificationChannel("location","Running Notifications",
                NotificationManager.IMPORTANCE_LOW)
            val notyManagerBack=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notyManagerBack.createNotificationChannel(channelBack)
        }
    }
}