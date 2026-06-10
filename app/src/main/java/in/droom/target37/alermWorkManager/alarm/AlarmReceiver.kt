package `in`.droom.target37.alermWorkManager.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val msg=intent?.getStringExtra("msg") ?: return
        println("Alarm Res: $msg")
    }
}