package com.example.tkppl_yes_248

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager


class ConnectionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, p1: Intent?) {
        val IsConnected = checkConnection(context)
        if (connectionReceiverListener != null)
            connectionReceiverListener!!.onNetworkConnectionChanged(isConnected)
    }

    interface ConnectionReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var connectionReceiverListener: ConnectionReceiverListener? = null
        val isConnected: Boolean
            get() {
                val cm = MyApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = cm.activeNetworkInfo
                return (activeNetwork != null && activeNetwork.isConnectedOrConnecting)
            }
    }

    private fun checkConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting)
    }
}