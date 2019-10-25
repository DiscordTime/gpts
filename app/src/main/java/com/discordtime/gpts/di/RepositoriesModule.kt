package com.discordtime.gpts.di

import com.discordtime.gpts.repository.IPlaceRepository
import com.discordtime.gpts.repository.PlaceRepository
import org.koin.dsl.module

val placeRepositoryModule = module {
    factory<IPlaceRepository> { PlaceRepository(get()) }
}