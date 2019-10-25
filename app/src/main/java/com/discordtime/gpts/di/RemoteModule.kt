package com.discordtime.gpts.di

import com.discordtime.gpts.listplaces.model.Place
import com.discordtime.gpts.remote.FirestoreAPI
import com.discordtime.gpts.remote.RemoteAPI
import com.discordtime.gpts.tools.Constants.PLACES_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

val remoteModules = module {
    single { FirebaseFirestore.getInstance() }
    single {get<FirebaseFirestore>().collection(PLACES_COLLECTION) }
    factory { FirestoreAPI(Place::class.java, get()) as RemoteAPI<Place> }
}