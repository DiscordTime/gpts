package com.discordtime.gpts.di

import com.discordtime.gpts.listplaces.model.Place
import com.discordtime.gpts.remote.FirestoreAPI
import com.discordtime.gpts.remote.RemoteAPI
import org.koin.dsl.module

val remoteModules = module {
    factory<RemoteAPI<Place>> { FirestoreAPI(get()) }
}