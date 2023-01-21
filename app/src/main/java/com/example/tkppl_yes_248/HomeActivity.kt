package com.example.tkppl_yes_248

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


private const val EXTRA_STATUS = "EXTRA_STATUS"
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //get data from pastActivity
        var intent = intent
        val homeFragment = HomeFragment()
        /*val shopFragment = shopFragment()*/
        val appointmentFragment = AppointmentFragment()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        makeCurrentFragment(homeFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                /*R.id.ic_shop -> makeCurrentFragment(shopFragment)*/
                R.id.ic_calender -> makeCurrentFragment(appointmentFragment)
            }
            true
        }
    }
    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            commit()
        }
}