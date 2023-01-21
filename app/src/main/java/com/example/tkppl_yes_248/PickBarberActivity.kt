package com.example.tkppl_yes_248

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.tkppl_yes_248.databinding.ActivityPickBarberBinding

class PickBarberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPickBarberBinding
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_pick_barber)

        binding = ActivityPickBarberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startTheService()

        Log.d("PickBarber", "Sedang di alihkan ke Barber dan menunggu respon")

        handler = Handler()
        handler.postDelayed({

            val intent = Intent(this, GetBarberActivity::class.java)
            startActivity(intent)
            finish()
            finish()
            stopTheService()
        }, 10000) // delay 10 seconds to GetBarberActivity
    }

    private fun stopTheService() {
        Toast.makeText(this, "Found a Barber", Toast.LENGTH_SHORT).show()
        stopService(Intent(this, GetBarberService::class.java))
    }

    private fun startTheService() {
        if(isMyserviceRunning(GetBarberService::class.java)){
            Toast.makeText(this, "Welcome Back !!!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Picking Barber", Toast.LENGTH_SHORT).show()
            startService(Intent(this,GetBarberService::class.java))
        }
    }

    private fun isMyserviceRunning(mClass: Class<GetBarberService>): Boolean {
        val manager: ActivityManager = getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager
        for(service: ActivityManager.RunningServiceInfo in
        manager.getRunningServices(Integer.MAX_VALUE)){
            if(mClass.name.equals(service.service.className)){
                return true
            }
        }
        return false
    }
}