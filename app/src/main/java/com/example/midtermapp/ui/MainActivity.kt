package com.example.midtermapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.midtermapp.R
import com.example.midtermapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.listItemFragment),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp)
        binding.topAppBar.setupWithNavController(navController,appBarConfiguration)

        navController.addOnDestinationChangedListener {_, destination, _ ->
            binding.topAppBar.isVisible = destination.id != R.id.startFragment
        }
        binding.topAppBar.setOnMenuItemClickListener {
            onMenuItemClicked(it)
        }


    }
    private fun onMenuItemClicked(menuItem: MenuItem) : Boolean {
        return when(menuItem.itemId) {
            R.id.cartFragment -> {
                menuItem.onNavDestinationSelected(navController)
            }
            else -> false
        }
    }

}