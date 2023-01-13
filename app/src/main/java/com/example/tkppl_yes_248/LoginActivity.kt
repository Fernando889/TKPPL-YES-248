package com.example.tkppl_yes_248

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener, ConnectionReceiver.ConnectionReceiverListener{

    lateinit var database : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if(!isConnected){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Disconnected")
            builder.setMessage("Your Connection is Lost")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                val intent = Intent(this@LoginActivity, IntroActivity::class.java)
                startActivity(intent)
            }
            builder.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //CheckConnection
        baseContext.registerReceiver(ConnectionReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        MyApplication.instance.setConnectionListener(this)

        btn_allreadyaccount.setOnClickListener(this)
        btn_createaccount.setOnClickListener(this)
        loginButton.setOnClickListener(this)
        signButton.setOnClickListener(this)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("App").child("users")

        //showPassword checkBox login
        showPassword.setOnClickListener {
            if (showPassword.isChecked()){
                passwordText_login.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                passwordText_login.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        //showPassword checkBox signUp
        showPassword_signup.setOnClickListener {
            if (showPassword_signup.isChecked()){
                passwordText_signup.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                passwordText_signup.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btn_allreadyaccount ->
            {
                login_layout.visibility = View.VISIBLE
                signup_layout.visibility = View.GONE
            }
            btn_createaccount ->
            {
                login_layout.visibility = View.GONE
                signup_layout.visibility = View.VISIBLE
            }
            loginButton ->
            {
                login()
            }
            signButton ->
            {
                signUp()
            }
        }
    }

    private fun login()
    {
        var username = usernameText_login.text.toString().trim()
        var password = passwordText_login.text.toString().trim()

        if (username.isEmpty() || password.isEmpty())
        {
            showToast("All Field Required")
        } else {
            isUsernameExist(username, password)
        }
    }

    private fun isUsernameExist(username: String, password: String)
    {
        // Read from the database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                var list = ArrayList<Signup_model>()
                var isusernameexist = false

                for (postsnapshot in dataSnapshot.children) {
                    var value = postsnapshot.getValue(Signup_model::class.java)

                    if (value!!.username == username && value!!.password == password)
                    {
                        isusernameexist = true
                    }
                    list.add(value!!)
                }

                if (isusernameexist)
                {
                    showToast("Login Successful")
                    //intent
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    finish()
                    Log.d("Login", "Berhasil Memasuki ke halaman Authentication")
                } else {
                    showToast("Login Failed! Check your username and password")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                showToast(error.toString())
            }
        })
    }

    private fun signUp()
    {
        var username = usernameText_signup.text.toString().trim()
        var password = passwordText_signup.text.toString().trim()

        if (username.isEmpty() || password.isEmpty())
        {
            showToast("All Field Required")
        } else {
            var id = databaseReference.push().key
            var model = Signup_model(username, password, id!!)

            //HERE DATA INSERTED
            databaseReference.child(id).setValue(model)
            showToast("Register Successful")

            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }
    }

    private fun showToast(text: String)
    {
        Toast.makeText(this@LoginActivity, text, Toast.LENGTH_SHORT).show()
    }
}