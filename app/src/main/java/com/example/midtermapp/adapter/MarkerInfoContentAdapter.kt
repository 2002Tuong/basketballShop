package com.example.midtermapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.midtermapp.databinding.MarketInfoContentBinding
import com.example.midtermapp.datanetwork.ShopLocation
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MarkerInfoContentAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(p0: Marker): View {
        val shopLocation = p0.tag as ShopLocation
        val binding = MarketInfoContentBinding.inflate(LayoutInflater.from(context))
        binding.textViewTitle.text = shopLocation.name
        binding.textViewAddress.text = shopLocation.address
        return binding.root
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }
}