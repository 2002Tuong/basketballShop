package com.example.midtermapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.midtermapp.R
import com.example.midtermapp.databinding.ActivityMainBinding
import com.example.midtermapp.datanetwork.DataSource


class MainActivity : AppCompatActivity()  {
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
        binding.topAppBar.setOnMenuItemClickListener {
            if(it.itemId == R.id.changeMode) {
                val intent = Intent(this, ShopActivity::class.java)
                startActivity(intent)
                true
            }else {
                false
            }
        }
        binding.bottomNavigationBar.setupWithNavController(navController)



    }


}

