package `in`.droom.target37.alermServiceWorkManager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import `in`.droom.target37.R
import `in`.droom.target37.alermServiceWorkManager.alarm.AlarmItems
import `in`.droom.target37.alermServiceWorkManager.alarm.AndroidAlarmManager
import `in`.droom.target37.databinding.ActivityAlermServiceWorkManagerBinding
import java.time.LocalDateTime

class AlermServiceWorkManagerActivity : AppCompatActivity() {
    private val TAG="AlermServiceWorkManagerActivity"
    private val binding by lazy { ActivityAlermServiceWorkManagerBinding.inflate(layoutInflater) }
    var alarmItems: AlarmItems?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
      initData()
    }

    private fun initData() {
        val androidAlarm= AndroidAlarmManager(this)
        binding.btnStart.setOnClickListener {
            val sec=binding.etSecond.text.toString().trim()
            val msg=binding.etMsg.text.toString().trim()
            if (sec.isNotEmpty() && msg.isNotEmpty()) {
                alarmItems = AlarmItems(time = LocalDateTime.now().plusSeconds(sec.toLong()), msg = msg)
                alarmItems?.let { androidAlarm.startAlarm(it) }
                binding.etSecond.setText("")
                binding.etMsg.setText("")
            }
        }
        binding.btnCancel.setOnClickListener {
            alarmItems?.let(androidAlarm::cancel)
        }
    }
}