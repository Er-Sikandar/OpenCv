package `in`.droom.target37.alermWorkManager.alarm

interface AlarmScheduler {
    fun startAlarm(item: AlarmItems)
    fun cancel(item: AlarmItems)
}