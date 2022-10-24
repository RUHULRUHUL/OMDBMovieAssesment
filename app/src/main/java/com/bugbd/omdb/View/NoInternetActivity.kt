package com.bugbd.omdb.View

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bugbd.omdb.R
import com.bugbd.omdb.databinding.ActivityNoInternetBinding
import com.bugbd.omdb.NetworkDetect

class NoInternetActivity : AppCompatActivity(), NetworkDetect.ConnectivityReceiverListener {

    private lateinit var binding: ActivityNoInternetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_no_internet)
        NetworkDetect.connectivityReceiverListener = this

        binding.checkInternetStatusId.setOnClickListener {
            var status = connection_status(this)
            if (status != null) {
                gotoMainActivity()
            } else {
                Toast.makeText(this, "Connectivity Check", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            onBackPressed()
        }
    }

    private fun gotoMainActivity() {
        onBackPressed()
    }

    private fun connection_status(context: Context): String? {
        var status: String? = null
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled"
                return status
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled"
                return status
            }
        } else {
            status = null
            return status
        }
        return status
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}