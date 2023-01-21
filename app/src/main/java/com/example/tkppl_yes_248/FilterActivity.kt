package com.example.tkppl_yes_248

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_filter.*
import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.tkppl_yes_248.R
import com.example.tkppl_yes_248.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class FilterActivity : AppCompatActivity() {
    private val IMAGE_CAPTURE_CODE = 1001
    private val PERMISSION_CODE = 1000;
    var imageUri: Uri? = null

    companion object{
        val INTENT_PARCELABLE = "OBJECT_INTENT"
        val IMAGE_REQUEST_CODE = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        Log.d("Filter", "Sedang Berada di Halaman Test Filter")

        //back actionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //detail
        val hairFilter =
            intent.getParcelableExtra<HairFilter>(ChooseFilterActivity.INTENT_PARCELABLE)

        val hairImage = findViewById<ImageView>(R.id.bgfilter)
        val hairName = findViewById<TextView>(R.id.tvNameFilter)

        hairImage.setImageResource(hairFilter?.hairFilter!!)
        hairName.text = hairFilter.hairName

        chooseImage_btn.setOnClickListener{
            pickImageGallery()
        }

        capture_btn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission was not enabled
                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    //permission already granted
                    openCamera()
                }
            } else {
                //system OS is < marshmallow
                openCamera()
            }
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.ACTION_ANSWER, imageUri)
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)

        Log.d("Filter", "Sedang Membuka Camera")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                } else {
                    //permission from popup was denied
                    Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //called when image was captured from camera Intent
        if (requestCode == IMAGE_REQUEST_CODE ||resultCode == Activity.RESULT_OK){
            //set image captured to image view
            bgphoto.setImageURI(imageUri)
        }

//        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
//            bgphoto.setImageURI(imageUri)
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}