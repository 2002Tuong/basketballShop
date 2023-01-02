package com.example.midtermapp.datanetwork

import android.content.Context
import com.example.midtermapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.io.InputStreamReader
//
//class ShoesReader(val context: Context) {
//    // GSON object responsible for converting from JSON to a Place object
//    private val gson = Gson()
//
//    // InputStream representing places.json
//    private val inputStream: InputStream
//        get() = context.resources.openRawResource(R.raw.shoes)
//
//    /**
//     * Reads the list of place JSON objects in the file places.json
//     * and returns a list of Shoes objects
//     */
//    fun read() : List<ShoesFirebase> {
//        val itemType = object : TypeToken<List<JsonShoes>>(){}.type
//        val reader = InputStreamReader (inputStream)
//        var count = -1
//        return gson.fromJson<List<JsonShoes>>(reader,itemType).map {
//            count += 1
//            it.toShoesFirebase(count)
//
//        }
//    }
//}