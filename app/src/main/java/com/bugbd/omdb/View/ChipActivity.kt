package com.bugbd.omdb.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bugbd.omdb.R
import com.bugbd.omdb.View.Fragment.ExploreFragment
import com.bugbd.omdb.View.Fragment.HomeFragment
import com.bugbd.omdb.View.Fragment.MoviesFragment
import com.bugbd.omdb.View.Fragment.SettingsFragment
import com.bugbd.omdb.databinding.ActivityChipBinding
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale.Category
@AndroidEntryPoint
class ChipActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChipBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayHome()
        binding.chipNav.setItemSelected(R.id.homeFragment,true)
        binding.chipNav.setOnItemSelectedListener(object :ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(id: Int) {
                var fragment: Fragment? = null
                when (id) {
                    R.id.homeFragment -> fragment = HomeFragment()
                    R.id.exploreFragment -> fragment = MoviesFragment()
                    R.id.categoriesFragment -> fragment = SettingsFragment()
                }
                if (fragment != null) {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.Fragment, fragment)
                    transaction.commit()
                }
            }

        })
    }

    private fun displayHome() {
        val homeFragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.Fragment, homeFragment)
        transaction.commit()
    }

}
