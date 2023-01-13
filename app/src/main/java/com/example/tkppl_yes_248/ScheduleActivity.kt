package com.example.tkppl_yes_248

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tkppl_yes_248.AlarmReceiver
import com.example.tkppl_yes_248.databinding.*
import com.example.tkppl_yes_248.databinding.ActivityScheduleBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.activity_schedule.*
import java.util.*

class ScheduleActivity : AppCompatActivity() {

    lateinit var binding: ActivityScheduleBinding
    lateinit var picker: MaterialTimePicker
    lateinit var calendar: Calendar
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        //Select Time Button
        binding.selectedTimeBtn.setOnClickListener {
            showTimePicker()
        }

        //Book Now Button
        binding.confirmBookTimeBtn.setOnClickListener {
            setAlarm()
        }

        //Cancel Book Button
        binding.cancelBookTimeBtn.setOnClickListener {
            cancelAlarm()
        }

        //back actionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun cancelAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.cancel(pendingIntent)

        Toast.makeText(this, "Booking Cancelled", Toast.LENGTH_LONG).show()
    }

    private fun setAlarm() {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent
        )

        Toast.makeText(this, "Booking Set Successfully", Toast.LENGTH_SHORT).show()
    }

    private fun showTimePicker() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Booking Time")
            .build()

        picker.show(supportFragmentManager, "SiniCukur")
        picker.addOnPositiveButtonClickListener {
            if (picker.hour > 12){
                binding.selectedTime.text = String.format("%02d", picker.hour - 12) + " : " + String.format("%02d", picker.minute) + " PM"
            } else {
                String.format("%02d", picker.hour) + " : " + String.format("%02d", picker.minute) + " AM"
            }

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name : CharSequence = "bookconfirmedReminderChannel"
            val description = "Booking Success"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("bookingconfirmed", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}