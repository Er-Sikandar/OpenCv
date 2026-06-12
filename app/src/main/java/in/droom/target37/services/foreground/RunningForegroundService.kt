package `in`.droom.target37.services.foreground

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import `in`.droom.target37.R
import `in`.droom.target37.utils.Actions

class RunningForegroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            Actions.START.toString()->start()
            Actions.STOP.toString()-> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("ForegroundServiceType")
    private fun start() {
    val noty= NotificationCompat.Builder(this,"running_channel")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("Run is active")
        .setContentText("Elapse time: 00:59")
        .build()
     startForeground(1,noty)
    }


}