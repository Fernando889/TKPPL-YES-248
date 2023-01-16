package com.example.tkppl_yes_248

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tkppl_yes_248.R

class ChooseFilterActivity : AppCompatActivity() {

    companion object{
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_filter)

        Log.d("Filter", "Sedang berada Di halaman Filter")

        //back actionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<RecyclerView>(R.id.rv1)
        val hairFilterList = listOf<HairFilter>(
            HairFilter(
                "HairStyle 1",
                R.drawable.filterhairstyle1
            ),
            HairFilter(
                "HairStyle 2",
                R.drawable.filterhairstyle2
            ),
            HairFilter(
                "HairStyle 3",
                R.drawable.filterhairstyle3
            ),
            HairFilter(
                "HairStyle 4",
                R.drawable.filterhairstyle4
            ),
            HairFilter(
                "HairStyle 5",
                R.drawable.filterhairstyle5
            ),
            HairFilter(
                "HairStyle 6",
                R.drawable.filterhairstyle6
            ),
            HairFilter(
                "HairStyle 7",
                R.drawable.filterhairstyle7
            ),
            HairFilter(
                "HairStyle 8",
                R.drawable.filterhairstyle8
            )
        )

        recyclerView.setHasFixedSize(true)
        val adapter = FilterAdapter(this, hairFilterList){
            val intent = Intent(this@ChooseFilterActivity, FilterActivity::class.java)
            intent.putExtra(INTENT_PARCELABLE, it)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}