package com.example.fide_rent

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fide_rent.databinding.ActivityLocationsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class LocationsFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityLocationsBinding

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
        mMap = googleMap

        val locationArray = location()
        val names = locationNames()

        for (i in locationArray!!.indices){

            mMap.addMarker(MarkerOptions().position(locationArray!![i]).title(getString(R.string.menu_pin) + ' ' + names[i]))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArray!![i]))
        }

        val costaRicaBounds = LatLngBounds(
            LatLng(8.15997680087243, -85.42375635762237),  // SW bounds
            LatLng(10.97459563406591, -82.77703354473935) // NE bounds
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(costaRicaBounds.center, 7f))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
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

    private fun locationNames():ArrayList<String>{
        val nameArrayList: ArrayList<String> = ArrayList<String>()

        nameArrayList.add("City Mall")
        nameArrayList.add("Jaco")
        nameArrayList.add("Libreria")
        nameArrayList.add("La Fortuna")
        nameArrayList.add("Puerto Viejo")

        return nameArrayList
    }
}