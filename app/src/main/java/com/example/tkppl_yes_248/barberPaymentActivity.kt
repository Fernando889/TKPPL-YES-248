package com.example.tkppl_yes_248

import android.annotation.TargetApi
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.Toast

class barberPaymentActivity : AppCompatActivity() {
    private lateinit var btnPay: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barber_payment)

        Log.d("Payment", "Sedang Berada di halaman Payment")

        //back actionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnPay = findViewById(R.id.btnPay)
        btnPay.setOnClickListener {
            customDialog()
            Log.d("Payment", "Transaksi telah berhasil")
        }

    }

    private fun customDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog)

        val btnOk = dialog.findViewById<Button>(R.id.btnOk)
        btnOk.setOnClickListener {
            dialog.dismiss()
            val intent6 = Intent(this@barberPaymentActivity, LoginActivity::class.java)
            startActivity(intent6)

            Log.d("Payment", "Memasuki ke Halaman Authentication")
        }

        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}