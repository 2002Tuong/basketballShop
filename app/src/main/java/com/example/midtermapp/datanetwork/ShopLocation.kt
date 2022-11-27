package com.example.midtermapp.datanetwork

import com.google.android.gms.maps.model.LatLng
import java.net.Inet4Address

data class ShopLocation(
    val latLng: LatLng,
    val name: String,
    val address: String
)