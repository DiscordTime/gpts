package com.discordtime.gpts.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.discordtime.gpts.tools.Constants.PLACES_COLLECTION
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreAPI<T>(val type: Class<T>): RemoteAPI<T> {

    private val db = FirebaseFirestore.getInstance()
    private val  collection: CollectionReference = db.collection(PLACES_COLLECTION)

    override fun get(): LiveData<List<T>> {

        val placesData = MutableLiveData<List<T>>()
        collection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) return@addSnapshotListener

            if (querySnapshot != null) {
                placesData.value = querySnapshot.toObjects(type)

            } else {
                placesData.value = emptyList()
            }
        }

        return placesData
    }

}