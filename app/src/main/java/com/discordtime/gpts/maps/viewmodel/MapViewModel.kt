package com.discordtime.gpts.maps.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.discordtime.gpts.R
import com.discordtime.gpts.listplaces.model.Place
import com.discordtime.gpts.repository.IPlaceRepository
import com.discordtime.gpts.repository.PlaceRepository
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapViewModel: ViewModel() {

    private val repository: IPlaceRepository = PlaceRepository()
    private lateinit var googleMap: GoogleMap

    private val placesData: LiveData<List<Place>> = repository.getPlaces()

    fun getMarkedPlaces() = placesData

    fun addMarker(place: Place, gMap: GoogleMap, context: Context) {
        val latLng = LatLng(place.geoPoint!!.latitude, place.geoPoint.longitude)
        googleMap = gMap
        googleMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(place.name)
                .snippet(place.description)
                .icon(generateBitmapDescriptorFromRes(context, R.drawable.ic_maker_poop)))
    }


    private fun generateBitmapDescriptorFromRes(context: Context?, resId: Int): BitmapDescriptor {
        val drawable = ContextCompat.getDrawable(context!!, resId)
        drawable!!.setBounds(0,0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}