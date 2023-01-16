package com.example.tkppl_yes_248

import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
/*import com.github.florent37.runtimepermission.kotlin.askPermission*/
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import org.w3c.dom.Text
import java.io.IOException
import java.util.*
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraIdleListener{

    var JobId= 10
    private var mMap: GoogleMap? = null
    lateinit var mapView: MapView
    private var MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
    private val DEFAULT_ZOOM = 15f
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    lateinit var tvCurrentAddress: TextView

    override fun onMapReady(googleMap: GoogleMap?) {
        mapView.onResume()
        mMap = googleMap

        getCurrentLocation()

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            return
        }
        //mMap!!.isMyLocationEnabled = true
        mMap!!.setMyLocationEnabled(true)
        mMap!!.setOnCameraMoveListener(this)
        mMap!!.setOnCameraMoveStartedListener(this)
        mMap!!.setOnCameraIdleListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        mapView = findViewById<MapView>(R.id.map1)
        tvCurrentAddress = findViewById<TextView>(R.id.tvAdd)

        getCurrentLocation()
        startMyJob()

        Log.d("Maps", "Sedang Berada di Menu Maps")

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        mapView = findViewById(R.id.map1)
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

        //back actionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //button Order Now
        val butNext = findViewById<Button>(R.id.orderButton)

        butNext.setOnClickListener{
            //intent
            val intent4 = Intent(this@MapsActivity, PickBarberActivity::class.java)
            startActivity(intent4)
            cancelMyJob()
        }
    }


    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        getCurrentLocation()
        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle == null){
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }

        mapView.onSaveInstanceState(mapViewBundle)
    }

//    private fun askPermissionLocation(){
//        askPermission(
//            android.Manifest.permission.ACCESS_FINE_LOCATION,
//            android.Manifest.permission.ACCESS_COARSE_LOCATION
//        ){
//            restartApp()
//            getCurrentLocation()
//            Log.d("Maps", "Permission Accepted")
//        }.onDeclined{ e ->
//            if (e.hasDenied()){
//                //the list of denied permissions
//                e.denied.forEach{
//                }
//
//                AlertDialog.Builder(this)
//                    .setMessage("Please accept our permissions.. Otherwise you will not be able to use some of our important Features.")
//                    .setPositiveButton("yes"){_,_ ->
//                        e.askAgain()
//                    } //ask again
//                    .setNegativeButton("no") {dialog, _ ->
//                        dialog.dismiss()
//                    }
//                    .show()
//
//                Log.d("Maps", "Permission Denied")
//            }
//        }
//    }

    private fun getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@MapsActivity)

        try{
            @SuppressLint("MissingPermission") val location =
                fusedLocationProviderClient!!.lastLocation
            location.addOnCompleteListener(object: OnCompleteListener<Location> {
                override fun onComplete(loc: Task<Location>){
                    if (loc.isSuccessful){
                        val currentLocation = loc.result as Location
                        if (currentLocation != null){
                            moveCamera(LatLng(currentLocation.latitude, currentLocation.longitude), DEFAULT_ZOOM)

                            Log.d("Maps", "Location Ditemukan")
                        }
                    } else {
                        getCurrentLocation()
                        /*Toast.makeText(this@MapsActivity, "Current Location not Found.", Toast.LENGTH_SHORT).show()*/

                        Log.w("Maps", "Location tidak Ditemukan")
                    }
                }
            })
        } catch (se: Exception) {
            Log.e("Maps", "Security Exception")
        }
    }

    private fun moveCamera(latLng: LatLng, zoom: Float){
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onLocationChanged(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address>? = null
        try{
            addresses = geocoder.getFromLocation(location!!.latitude, location.longitude, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        setAddress(addresses!![0])
        Log.d("Maps", "Location telah Diubah")
    }

    private fun setAddress(addresses: Address) {
        if (addresses != null){
            if (addresses.getAddressLine(0) != null) {
                tvCurrentAddress!!.setText(addresses.getAddressLine(0))
            }

            if (addresses.getAddressLine(1) != null) {
                tvCurrentAddress.setText(tvCurrentAddress.text.toString() + addresses.getAddressLine(1))
            }
        }
    }

    override fun onCameraMove() {

    }

    override fun onCameraMoveStarted(p0: Int) {

    }

    override fun onCameraIdle() {
        var addresses: List<Address>? = null
        val geocoder = Geocoder(this, Locale.getDefault())

        try {
            addresses = geocoder.getFromLocation(mMap!!.cameraPosition.target.latitude, mMap!!.cameraPosition.target.longitude, 1)
            setAddress(addresses!![0])
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun startMyJob() {
        var serviceComponent = ComponentName(this,CekCuaca::class.java)
        val mJobInfo = JobInfo.Builder(JobId,serviceComponent)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setRequiresDeviceIdle(false)
            .setRequiresCharging(false)
            .setPeriodic(15*60*1000)
        var JobCuaca = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        JobCuaca.schedule(mJobInfo.build())
    }
    private fun cancelMyJob() {
        var JobCuaca = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        JobCuaca.cancel(JobId)
    }

    private fun restartApp(){
        val i = Intent(this, MapsActivity::class.java)
        startActivity(i)
        finish()

        Log.d("Maps", "Restart Maps")
    }
}