package `in`.droom.target37.alermServiceWorkManager.alarm

interface AlarmScheduler {
    fun startAlarm(item: AlarmItems)
    fun cancel(item: AlarmItems)
}