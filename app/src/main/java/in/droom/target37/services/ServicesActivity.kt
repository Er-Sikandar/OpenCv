package `in`.droom.target37.services

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import `in`.droom.target37.R
import `in`.droom.target37.databinding.ActivityServicesBinding
import `in`.droom.target37.services.background.RunLocationService
import `in`.droom.target37.utils.Actions
import `in`.droom.target37.services.foreground.RunningForegroundService

class ServicesActivity : AppCompatActivity() {
    private val binding by lazy { ActivityServicesBinding.inflate(layoutInflater) }
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
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),0)
        }else{
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),0)
        }
         binding.apply {
             btnStart.setOnClickListener {
                 Intent(applicationContext, RunLocationService::class.java).apply {
                     action= RunLocationService.ACTION_START
                     startService(this)
                 }
                 /*Intent(applicationContext, RunningForegroundService::class.java).also {
                     it.action= Actions.START.name
                      startService(it)
                  }*/
             }
             btnCancel.setOnClickListener {
                 Intent(applicationContext, RunLocationService::class.java).apply {
                     action= RunLocationService.ACTION_STOP
                     startService(this)
                 }
                 /*Intent(applicationContext, RunningForegroundService::class.java).also {
                     it.action= Actions.STOP.name
                     startService(it)
                 }*/
             }
         }
    }
}