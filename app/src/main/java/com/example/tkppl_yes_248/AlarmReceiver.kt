package com.example.tkppl_yes_248

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.tkppl_yes_248.DetailActivity
import com.example.tkppl_yes_248.R
import com.example.tkppl_yes_248.TrackOrderActivity
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val i = Intent(context, TrackOrderActivity::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)

        Log.d("AlarmReceiver", "Berhasil Berjalan pada jam " + Date().toString())

        Log.w("AlarmReceiver", "Alarm Berhasil")
        val notifyId = 30103
        val channelId = "my_channel_01"
        val name = "ON/OFF"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val nNotifyChannel = NotificationChannel(channelId,
            name,
            importance)
        val mBuilder = NotificationCompat.Builder(context!!,channelId)
            .setSmallIcon(R.drawable.logosinicukur)
            .setContentText("Your Booking has been confirmed")
            .setContentTitle("Sini Cukur : Booking Confirmed")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
        var mNotificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        for(s in mNotificationManager.notificationChannels){
            mNotificationManager.deleteNotificationChannel(s.id)
        }
        mNotificationManager.createNotificationChannel(nNotifyChannel)
        mNotificationManager.notify(notifyId,mBuilder.build())
    }
}