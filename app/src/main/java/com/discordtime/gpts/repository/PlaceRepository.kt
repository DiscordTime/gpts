package com.discordtime.gpts.repository

import androidx.lifecycle.LiveData
import com.discordtime.gpts.listplaces.model.Place
import com.discordtime.gpts.remote.FirestoreAPI
import com.discordtime.gpts.remote.RemoteAPI

class PlaceRepository: IPlaceRepository {
    
    private val remoteAPI: RemoteAPI<Place> = FirestoreAPI(Place::class.java)

    override fun getPlaces(): LiveData<List<Place>> {
        return remoteAPI.get()
    }

}