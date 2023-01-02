package com.example.midtermapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.midtermapp.R
import com.example.midtermapp.databinding.ActivityShopBinding

/*
This activity for shop to edit item of shop
 */
class ShopActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityShopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            as NavHostFragment

        navController = navHostFragment.navController
        binding.topAppBar.setupWithNavController(navController)
    }
}