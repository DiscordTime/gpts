package com.discordtime.gpts.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.discordtime.gpts.listplaces.model.Place
import com.discordtime.gpts.tools.Constants.PLACES_COLLECTION
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreAPI<T>(val type: Class<T>, val collection: CollectionReference): RemoteAPI<T> {

    override fun get(): LiveData<List<T>> {

        val data = MutableLiveData<List<T>>()

        collection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) return@addSnapshotListener

            if (querySnapshot != null) {
                data.value = querySnapshot.toObjects(type)
            } else {
                data.value = emptyList()
            }
        }


        return data
    }

}