package com.thoughtworks.rockveganfood.di

import androidx.room.Room
import com.thoughtworks.rockveganfood.data.dao.AppDatabase
import com.thoughtworks.rockveganfood.data.localdatasource.MenuLocalDataSource
import com.thoughtworks.rockveganfood.data.repository.OrderRepository
import com.thoughtworks.rockveganfood.ui.OrderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val repositoryModule = module {
    factory { OrderRepository(get()) }
}

private val viewModelModule = module {
    viewModel { OrderViewModel(get()) }
}

private val storageModule = module {
    single {
        Room
            .databaseBuilder(get(), AppDatabase::class.java, "rock-vegan-food")
            .createFromAsset("database/rockveganfood.db")
            .build()
    }
    single { MenuLocalDataSource(get()) }
}

val appModule = repositoryModule + viewModelModule + storageModule
