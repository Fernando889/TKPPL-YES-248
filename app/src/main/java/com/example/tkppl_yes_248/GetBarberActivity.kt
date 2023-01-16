package com.example.tkppl_yes_248

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_get_barber.*

class GetBarberActivity : AppCompatActivity() {

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_barber)

        Log.d("GetBarber", "Barber telah ditemukan")

        trackOrderButton.setOnClickListener {
            val intent = Intent(this@GetBarberActivity, TrackOrderActivity::class.java)
            startActivity(intent)
        }
    }
}