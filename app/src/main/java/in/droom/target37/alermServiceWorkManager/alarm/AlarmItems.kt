package `in`.droom.target37.alermServiceWorkManager.alarm

import java.time.LocalDateTime

data class AlarmItems(
    val time: LocalDateTime,
    val msg: String
)