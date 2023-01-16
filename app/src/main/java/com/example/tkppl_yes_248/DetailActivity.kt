package com.example.tkppl_yes_248

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class DetailActivity : AppCompatActivity() {

    val choice = arrayOf("No", "Shave")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //back actionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //detail
        val hairstyle = intent.getParcelableExtra<HairStyle>(HomeFragment.INTENT_PARCELABLE)

        val hairImage = findViewById<ImageView>(R.id.img_item_photo)
        val hairName = findViewById<TextView>(R.id.tv_item_name)
        val hairPrice = findViewById<TextView>(R.id.tv_item_price)

        hairImage.setImageResource(hairstyle?.hairImage!!)
        hairName.text = hairstyle.hairName
        hairPrice.text = "Harga                   " + hairstyle.hairPrice

        //Spinner
//        val spinner = findViewById<Spinner>(R.id.spinner)
//        val arrayAdapter = ArrayAdapter(this@DetailActivity,android.R.layout.simple_spinner_dropdown_item, choice)
//        spinner.setAdapter(arrayAdapter);
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                Toast.makeText(this@DetailActivity, "You Select "+choice[position], Toast.LENGTH_LONG).show()
//                Log.d("Detail", "User sedang memilih Menu Tambahan = ${choice[position]}")
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//
//        }

        //buttonNext Order
        val butNext = findViewById<Button>(R.id.nextButton)

        butNext.setOnClickListener{
            //intent
            val intent3 = Intent(this@DetailActivity, MapsActivity::class.java)
            intent3.putExtra("Harga", hairPrice.text)
            startActivity(intent3)

            Log.d("Detail", "Sedang memasuki ke halaman Detail")
        }

        Log.d("Detail", "Sedang memasuki ke halaman Detail")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}