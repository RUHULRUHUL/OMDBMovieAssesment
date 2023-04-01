package com.bugbd.omdb.View

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bugbd.omdb.R
import com.bugbd.omdb.databinding.ActivityMainBinding
import com.bugbd.omdb.NetworkDetect
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NetworkDetect.ConnectivityReceiverListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var networkDetect: NetworkDetect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        checkInternetStatus()
        networkDetect = NetworkDetect()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.Fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        //binding.bottomNav.menu[1].isVisible = false
    }

    private fun bottomIconSize() {
        val view: View =
            binding.bottomNav.getChildAt(1).findViewById(com.google.android.material.R.id.icon)
        val layoutParams = view.layoutParams
        val displayMetrics = resources.displayMetrics
        layoutParams.height =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, displayMetrics).toInt()
        layoutParams.width = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            80f, displayMetrics
        ).toInt()
        view.layoutParams = layoutParams
    }

    private fun checkInternetStatus() {
        val status = connectionStatus(this)
        if (status != null) {

        } else {
            Toast.makeText(this, "Connectivity Check", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, NoInternetActivity::class.java))
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        NetworkDetect.connectivityReceiverListener = this
        registerReceiver(networkDetect, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkDetect)
    }

    private fun connectionStatus(context: Context): String? {
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

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            // no internet connect
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, NoInternetActivity::class.java))
        }
    }


}