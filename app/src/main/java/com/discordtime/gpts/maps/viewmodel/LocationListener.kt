package com.discordtime.gpts.maps.viewmodel

import android.location.Location

interface LocationListener {

    fun onLocationChanged(location: Location)
}