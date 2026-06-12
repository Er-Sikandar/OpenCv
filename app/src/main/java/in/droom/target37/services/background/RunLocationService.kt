package `in`.droom.target37.services.background

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import `in`.droom.target37.R
import `in`.droom.target37.utils.Actions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class RunLocationService : Service() {
    private val serviceScope= CoroutineScope(SupervisorJob()+ Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    companion object{
        const val ACTION_START="action_start"
        const val ACTION_STOP="action_stop"
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient= DefaultLocationClient(applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ACTION_START -> startWork()
            ACTION_STOP ->stopWork()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startWork() {
        val noty= NotificationCompat.Builder(this,"location")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Tracking location")
            .setContentText("Location: null")
            .setOngoing(true)
            .build()
         startForeground(1,noty)

    }
    private fun stopWork() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}