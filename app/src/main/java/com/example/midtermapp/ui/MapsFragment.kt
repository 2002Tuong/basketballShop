package com.example.midtermapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.midtermapp.BitmapHelper
import com.example.midtermapp.R
import com.example.midtermapp.adapter.MarkerInfoContentAdapter
import com.example.midtermapp.datanetwork.DataSource
import com.example.midtermapp.datanetwork.ShopLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

const val REQUEST_LOCATION_PERMISSION = 10
@RequiresApi(Build.VERSION_CODES.M)
class MapsFragment : Fragment() {
    private val shopLocation:  List<ShopLocation> by lazy {
        DataSource.loadLocations()
    }
    private val basketballIcon: BitmapDescriptor by lazy {
        BitmapHelper.vectorToBitmap(requireContext(),R.drawable.ic_sports_basketball_24)
    }
    @SuppressLint("PotentialBehaviorOverride")
    private val callback = OnMapReadyCallback { googleMap ->

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        googleMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMyLocationButtonEnabled = true
        }
        addMarker(googleMap)
        googleMap.setInfoWindowAdapter(MarkerInfoContentAdapter(requireContext()))
        currentLocation(requireContext(),googleMap)
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
    private fun addMarker(gMap: GoogleMap) {
        shopLocation.forEach {
            gMap.addMarker(
                MarkerOptions()
                    .position(it.latLng)
                    .title(it.name)
                    .icon(basketballIcon)

            )?.tag = it
        }
    }

    private fun currentLocation(context: Context, gMap: GoogleMap) {
        val flpCient = LocationServices.getFusedLocationProviderClient(context)
        if(ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locateMe(flpCient,gMap)
        }else {
            permissionsResultCallback.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val permissionsResultCallback = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){
        when (it) {
            true -> { println("Permission has been granted by user") }
            false -> {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()

            }
        }
    }


    private fun locateMe(client: FusedLocationProviderClient, gMap: GoogleMap) {
        client.lastLocation.addOnSuccessListener {
            if (it != null) {
                val curLocation = LatLng(it.latitude, it.longitude)
                gMap.moveCamera(CameraUpdateFactory.newLatLng(curLocation))
                gMap.addMarker(
                    MarkerOptions().position(curLocation)
                )
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curLocation, 13f))

            } else {
                gMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(10.4, 100.2)))
            }
        }
    }
}