package com.discordtime.gpts.maps.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.discordtime.gpts.R
import com.discordtime.gpts.maps.viewmodel.MapViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.firebase.FirebaseApp
import org.koin.android.viewmodel.ext.android.viewModel

class MapViewFragment : Fragment(), OnMapReadyCallback {

    private val mapViewModel: MapViewModel by viewModel()
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
        //mapViewModel = ViewModelProviders.of(this)[MapViewModel::class.java]
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap.isMyLocationEnabled

        // test
        mapViewModel.getMarkedPlaces().observe(this, Observer {
            it.forEach {
                    place ->
                Log.d("MapViewFragment","${place.name}")
                mapViewModel.addMarker(place, gMap, view!!.context)
            }
        })

        // For dropping a marker at a point on the Map
        val recife = LatLng(-8.055747, -34.871044)

        // For zooming automatically to the location of the marker
        val cameraPosition = CameraPosition.Builder().target(recife).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}