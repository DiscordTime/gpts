package com.discordtime.gpts.maps.view

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.discordtime.gpts.R
import com.discordtime.gpts.maps.viewmodel.LocationListener
import com.discordtime.gpts.maps.viewmodel.LocationManager
import com.discordtime.gpts.maps.viewmodel.MapViewModel
import com.discordtime.gpts.tools.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import org.koin.android.viewmodel.ext.android.viewModel

class MapViewFragment : Fragment(), OnMapReadyCallback, LocationListener {

    private val mapViewModel: MapViewModel by viewModel()
    private lateinit var mContext: Context
    private lateinit var mMapView: MapView
    private lateinit var mGoogleMap: GoogleMap
    private lateinit var mLocationManager: LocationManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMapView = view.findViewById(R.id.mapView) as MapView
        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()
        mMapView.getMapAsync(this)

        mLocationManager = LocationManager(mContext, this)
        mLocationManager.startLocationUpdates()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onMapReady(gMap: GoogleMap) {
        mGoogleMap = gMap
        mGoogleMap.isMyLocationEnabled = true //enables location button (blue)
        mGoogleMap.uiSettings.isCompassEnabled = false
        mGoogleMap.uiSettings.isRotateGesturesEnabled = false
        mGoogleMap.uiSettings.isTiltGesturesEnabled = false

         //test
        mapViewModel.getMarkedPlaces().observe(this, Observer {
            it.forEach {
                    place ->
                Log.d("MapViewFragment","${place.name}")
                mapViewModel.addMarker(place, gMap, view!!.context)
            }
        })
    }

    override fun onLocationChanged(location: Location) {
        val currentLocation = LatLng(location.latitude, location.longitude)

        // For zooming automatically to the location of the marker
        val cameraPosition = CameraPosition.Builder()
            .target(currentLocation)
            .zoom(Constants.CAMERA_MAPS_ZOOM)
            .build()

        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}