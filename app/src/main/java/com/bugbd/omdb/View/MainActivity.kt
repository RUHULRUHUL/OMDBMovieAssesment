package com.bugbd.omdb.View

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bugbd.omdb.NetworkDetect
import com.bugbd.omdb.R
import com.bugbd.omdb.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NetworkDetect.ConnectivityReceiverListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var networkDetect: NetworkDetect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.bugbd.omdb.R.layout.activity_main)
        checkInternetStatus()
        networkDetect = NetworkDetect()



       // getMenuInflater().inflate(R.menu.main, menu);

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.Fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.settingsFragment -> {
                    Glide.with(this).load(R.drawable.live_icon).into(binding.tofront)
                    Toast.makeText(this, "center button clicked", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    binding.tofront.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.live_icon
                        )
                    )
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.nav_bottom_menu,menu)
        val menuItem  = menu?.findItem(R.id.exploreFragment)
        Glide.with(this).asBitmap().load(R.drawable.live_gif)
            .into(object : SimpleTarget<Bitmap?>(35, 35) {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    if (menuItem != null) {
                        runOnUiThread {
                            menuItem.icon = BitmapDrawable(resources, resource)
                        }
                    }
                }
            })

        return true
    }

//    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//        val settingsItem = menu?.findItem(R.id.exploreFragment)
//        Glide.with(this).asBitmap().load(R.drawable.live_gif)
//            .into(object : SimpleTarget<Bitmap?>(35, 35) {
//                override fun onResourceReady(
//                    resource: Bitmap,
//                    transition: Transition<in Bitmap?>?
//                ) {
//                    if (settingsItem != null) {
//                        runOnUiThread {
//                            settingsItem.icon = BitmapDrawable(resources, resource)
//                        }
//
//                    }
//                }
//            })
//        return super.onPrepareOptionsMenu(menu)
//    }

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