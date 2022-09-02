package com.jaehyeon.alarmmanagertest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jaehyeon.alarmmanagertest.databinding.ActivityMainBinding
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val manager: AlarmManager by lazy {
        getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
    private lateinit var pIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreate0.setOnClickListener {
            pIntent = Intent(this, AlarmBroadCastReceiver::class.java).apply {
                putExtra("data", "00")
            }.let {
                PendingIntent.getBroadcast(applicationContext, 0, it,  PendingIntent.FLAG_IMMUTABLE)
            }

            manager.setRepeating(AlarmManager.RTC_WAKEUP, setBaseTime(0).timeInMillis, AlarmManager.INTERVAL_HALF_HOUR, pIntent)
        }

        binding.btnCreate12.setOnClickListener {
            pIntent = Intent(this, AlarmBroadCastReceiver::class.java).apply {
                putExtra("data", "01")
            }.let {
                PendingIntent.getBroadcast(applicationContext, 0, it,  PendingIntent.FLAG_IMMUTABLE)
            }

            manager.setRepeating(AlarmManager.RTC_WAKEUP, setBaseTime(12).timeInMillis, 1000L * 300, pIntent)
        }

        binding.btnCreate18.setOnClickListener {
            pIntent = Intent(this, AlarmBroadCastReceiver::class.java).apply {
                putExtra("data", "02")
            }.let {
                PendingIntent.getBroadcast(applicationContext, 0, it,  PendingIntent.FLAG_IMMUTABLE)
            }

            manager.setRepeating(AlarmManager.RTC_WAKEUP, setBaseTime(18).timeInMillis, AlarmManager.INTERVAL_DAY, pIntent)
        }

    }

    private fun setBaseTime(baseHour: Int): Calendar {
        val today = LocalDate.now()
        val todayCalendar = Calendar.getInstance()
        val baseTime = Calendar.getInstance().apply {
            set(today.year, today.monthValue -1 , today.dayOfMonth, baseHour, 0)
        }

        return if (todayCalendar.time.time < baseTime.time.time) {
            Calendar.getInstance().apply {
                set(today.year, today.monthValue -1 , today.dayOfMonth - 1, baseHour, 0)
            }
        } else {
            baseTime
        }
    }
}