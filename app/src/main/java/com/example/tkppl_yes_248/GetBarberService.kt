package com.example.tkppl_yes_248

import android.app.*
import android.app.NotificationManager
import android.app.NotificationChannel
import android.app.Notification
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.tkppl_yes_248.Constants.Channel_ID
import com.example.tkppl_yes_248.Constants.Notification_ID

class GetBarberService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate(){
        super.onCreate()
        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        val notificationIntent = Intent(this, PickBarberActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,0,notificationIntent,0
        )
        val notification = Notification
            .Builder(this, Channel_ID)
            .setContentText("Finding a Barber...")
            .setSmallIcon(R.drawable.logosinicukur)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(Notification_ID, notification)
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                Channel_ID,"My Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(serviceChannel)
        }
    }
}