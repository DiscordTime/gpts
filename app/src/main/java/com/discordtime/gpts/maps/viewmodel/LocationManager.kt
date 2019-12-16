package com.discordtime.gpts.maps.viewmodel

import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*

class LocationManager(val mContext: Context, private val locationListener: LocationListener) {

    private val UPDATE_INTERVAL: Long = 2 * 60 * 1000 //2 mins
    private val FASTEST_INTERVAL: Long = 40 * 1000 //40 segs

    private val mLocationRequest = LocationRequest()
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    fun startLocationUpdates() {
        //updates properties
        mLocationRequest.apply {
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            interval = UPDATE_INTERVAL
            fastestInterval = FASTEST_INTERVAL
        }

        var builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        var locationSettingsRequest = builder.build()

        //checks if location settings are satisfied
        var settingsClient = LocationServices.getSettingsClient(mContext)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext)
        mFusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location ->
            onLocationChanged(location)
        }
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null) {
                    onLocationChanged(locationResult.lastLocation)
                }
            }

        }, Looper.myLooper())
    }

    private fun onLocationChanged(location: Location) {
        locationListener.onLocationChanged(location)
    }
}
