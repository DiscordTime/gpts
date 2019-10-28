package com.discordtime.gpts.di

import com.discordtime.gpts.listplaces.viewmodel.ListPlacesViewModel
import com.discordtime.gpts.maps.viewmodel.MapViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ListPlacesViewModel(get()) }
    viewModel { MapViewModel(get()) }
}