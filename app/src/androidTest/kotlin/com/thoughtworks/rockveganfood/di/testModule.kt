package com.thoughtworks.rockveganfood.di

import androidx.room.Room
import com.thoughtworks.rockveganfood.data.dao.AppDatabase
import org.koin.dsl.module

private val testStorageModule = module {
    single(override = true) {
        Room.inMemoryDatabaseBuilder(
            get(),
            AppDatabase::class.java
        ).build()
    }
}

val testModule = testStorageModule