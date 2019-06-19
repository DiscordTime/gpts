package com.discordtime.gpts.maps.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.discordtime.gpts.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng

class MapViewFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMapView = view.findViewById(R.id.mapView) as MapView
        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()
        mMapView.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap.isMyLocationEnabled

        // For dropping a marker at a point on the Map
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"))

        // For zooming automatically to the location of the marker
        val cameraPosition = CameraPosition.Builder().target(sydney).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}