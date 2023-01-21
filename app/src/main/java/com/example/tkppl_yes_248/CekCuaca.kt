package com.example.tkppl_yes_248

import android.app.Notification
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.tkppl_yes_248.R
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject


class CekCuaca : JobService(){
    val AppID = "cccdf75d45768caa8a9c5840e3319465"
    val Kota = "Medan"
    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.w("TAG","START JOB")
        getCuacaHariIni(p0)
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.w("TAG","STOP JOB")
        return true
    }

    private fun getCuacaHariIni(p0: JobParameters?) {
        var client = AsyncHttpClient()
        var url = "http://api.openweathermap.org/data/2.5/weather?q=$Kota&AppID=$AppID"
        val charset = Charsets.UTF_8
        var content = ""
        var contentExtend = ""

        var handler = object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                var result : String=""
                if (responseBody != null){
                    result = responseBody.toString(charset)

                    val obj = JSONObject(result)
                    val main = obj.getJSONArray("weather").getJSONObject(0).getString("main")
                    val description = obj.getJSONArray("weather").getJSONObject(0).getString("description")
                    val tempMin = obj.getJSONObject("main").getString("temp_min")
                    val tempMax = obj.getJSONObject("main").getString("temp_max")

                    content = main
                    contentExtend = "Description : $description\nTemp : $tempMin - $tempMax"
                }

                Log.w("TAG",result)
                getNotification(content, contentExtend)

                jobFinished(p0,false)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                jobFinished(p0,true)
                Log.w("TAG", "Gagal")
            }
        }
        client.get(url,handler)
    }

    fun getNotification(content: String, contentExtend: String){
        val Channel_id = "my_channel_01"
        val mBuilder = NotificationCompat.Builder(applicationContext!!, Channel_id)
            .setSmallIcon(R.drawable.logosinicukur)
            .setContentTitle("Today Weather")
            .setContentText(content)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentExtend))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val mNotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = 30103
        mNotificationManager.notify(id, mBuilder.build())
        Log.d("Notif Cuaca", "Berhasil Muncul")
    }
}