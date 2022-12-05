package com.example.fide_rent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.fide_rent.databinding.ActivityLocationsBinding
import com.google.android.gms.maps.model.LatLngBounds

class LocationsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityLocationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val locationArray = location()

        for (i in locationArray!!.indices){

            mMap.addMarker(MarkerOptions().position(locationArray!![i]).title(getString(R.string.menu_pin)))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArray!![i]))
        }

        val costaRicaBounds = LatLngBounds(
            LatLng(8.15997680087243, -85.42375635762237),  // SW bounds
            LatLng(10.97459563406591, -82.77703354473935) // NE bounds
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(costaRicaBounds.center, 7f))

    }


    private fun location(): ArrayList<LatLng> {

        val cityMall =LatLng(10.004853, -84.210465)
        val jaco =LatLng(9.616856, -84.631077 )
        val liberia =LatLng(10.603192182748275, -85.51324780742007)
        val fortuna =LatLng(10.473428, -84.638636)
        val puertoViejo =LatLng(9.654182715673244, -82.76501541651474)

        val locationArrayList: ArrayList<LatLng> = ArrayList<LatLng>()

        locationArrayList.add(cityMall)
        locationArrayList.add(jaco)
        locationArrayList.add(liberia)
        locationArrayList.add(fortuna)
        locationArrayList.add(puertoViejo)

        return locationArrayList
    }

}