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
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        mLocationRequest.interval = UPDATE_INTERVAL
        mLocationRequest.fastestInterval = FASTEST_INTERVAL

        var builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        var locationSettingsRequest = builder.build()

        //checks if location settings are satisfied
        var settingsClient = LocationServices.getSettingsClient(mContext)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext)
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                onLocationChanged()
            }

        }, Looper.myLooper())
    }

    private fun onLocationChanged() {
        //var msg = "location changed: " + location?.latitude + ", " + location?.longitude
        //Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()

        mFusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location ->
            locationListener.onLocationChanged(location)
        }
    }
}
