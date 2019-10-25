package com.discordtime.gpts.listplaces.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class Place (
    val created_on: Timestamp? =  null,
    val description: String? =  null,
    val geoPoint: GeoPoint? =  null,
    val name: String? =  null,
    val place_id: String? =  null,
    val rating: Int? =  null
)
