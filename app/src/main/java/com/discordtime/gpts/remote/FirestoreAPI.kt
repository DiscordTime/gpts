package com.discordtime.gpts.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.discordtime.gpts.tools.Constants.PLACES_COLLECTION
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreAPI<T>(val type: Class<T>, val collection: CollectionReference): RemoteAPI<T> {

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