package `in`.droom.target37.alermWorkManager

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import `in`.droom.target37.R
import `in`.droom.target37.alermWorkManager.alarm.AlarmItems
import `in`.droom.target37.alermWorkManager.alarm.AndroidAlarmManager
import `in`.droom.target37.alermWorkManager.workmanager.WorkManagerWorker
import `in`.droom.target37.databinding.ActivityAlermServiceWorkManagerBinding
import java.time.LocalDateTime
import kotlin.jvm.java

class AlermWorkManagerActivity : AppCompatActivity() {
    private val TAG="AlermServiceWorkManagerActivity"
    private val binding by lazy { ActivityAlermServiceWorkManagerBinding.inflate(layoutInflater) }
    var alarmItems: AlarmItems?=null
    private lateinit var workmanager: WorkManager
    val threeViewModel by viewModels<ThreeViewModel>()

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val uri=if (Build.VERSION.SDK_INT>= 33){
            intent?.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        }else{
          intent?.getParcelableExtra(Intent.EXTRA_STREAM,)
        } ?: return
        Log.e(TAG,"onNewIntent: $uri")
        threeViewModel.updateUnCompressUri(uri)
        val request= OneTimeWorkRequestBuilder<WorkManagerWorker>().setInputData(
            workDataOf(
                WorkManagerWorker.CONT_URI to uri.toString(),
                WorkManagerWorker.KEY_CMP to 1024*20L)
        ).setConstraints(Constraints(requiresStorageNotLow = true))
            .build()
        threeViewModel.updateWorkId(request.id)
        workmanager.enqueue(request)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        workmanager= WorkManager.getInstance(this)
        threeViewModel.workId.observe(this) {wId->
            wId?.let {
                workmanager.getWorkInfoByIdLiveData(it).observe(this){workInfo ->
                    if (workInfo != null) {
                        when (workInfo.state) {
                            WorkInfo.State.SUCCEEDED -> {
                                val path = workInfo.outputData.getString(WorkManagerWorker.RES_PATH)
                                path?.let {
                                    val bitmap= BitmapFactory.decodeFile(it)
                                    threeViewModel.updateCompressBitmap(bitmap)
                                }
                            }
                            WorkInfo.State.FAILED -> {
                                Log.e(TAG,"Failed: $it")
                            }

                            WorkInfo.State.RUNNING -> {
                                Log.e(TAG,"Running: $it")
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
        threeViewModel.uri.observe(this) { it ->
            Log.e(TAG,"Uri: $it")
        }
         threeViewModel.bitmap.observe(this) {
             Log.e(TAG,"Bitmap: $it")
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