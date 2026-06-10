package `in`.droom.target37.alermWorkManager.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.time.ZoneId

class AndroidAlarmManager(val context: Context) : AlarmScheduler{
   private val alarmManager=context.getSystemService(AlarmManager::class.java)

    override fun startAlarm(item: AlarmItems) {
        val intent= Intent(context, AlarmReceiver::class.java).apply {
            putExtra("msg",item.msg)
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,item.time.atZone(ZoneId.systemDefault()).toEpochSecond()*1000,
            PendingIntent.getBroadcast(context,item.hashCode(),intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE))
        /*alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
            item.time.atZone(ZoneId.systemDefault()).toEpochSecond()*1000,
            PendingIntent.getBroadcast(context,item.hashCode(),intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE))
    */
    }

    override fun cancel(item: AlarmItems) {
        val intent= Intent(context, AlarmReceiver::class.java)
        alarmManager.cancel {
            PendingIntent.getBroadcast(context,item.hashCode(),intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
    }
}