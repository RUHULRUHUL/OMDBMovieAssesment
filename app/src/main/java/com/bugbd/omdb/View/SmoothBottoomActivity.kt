package com.bugbd.omdb.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.bugbd.omdb.R
import com.bugbd.omdb.databinding.ActivitySmoothBottoomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SmoothBottoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySmoothBottoomBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmoothBottoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.Fragment)
        setupSmoothBottomMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_bottom_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeFragment -> {
                Toast.makeText(this, "Home fragment", Toast.LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.nav_bottom_menu)
        val menu = popupMenu.menu
        binding.bottomBar.setupWithNavController(menu, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}