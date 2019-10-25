package com.discordtime.gpts.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.discordtime.gpts.listplaces.model.Place
import com.discordtime.gpts.tools.Constants.PLACES_COLLECTION
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreAPI: RemoteAPI<Place> {

    private val db = FirebaseFirestore.getInstance()
    private val  collection: CollectionReference = db.collection(PLACES_COLLECTION)

    override fun get(): LiveData<List<Place>> {

        val placesData = MutableLiveData<List<Place>>()

        collection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) return@addSnapshotListener

            if (querySnapshot != null) {
                placesData.value = querySnapshot.toObjects(Place::class.java)
            } else {
                placesData.value = emptyList()
            }
        }


        return placesData
    }

}