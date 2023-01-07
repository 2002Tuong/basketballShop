package com.example.midtermapp.datanetwork

import com.google.gson.annotations.SerializedName

data class Model3D(
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val url: String)
