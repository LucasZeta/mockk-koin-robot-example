package com.thoughtworks.rockveganfood.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thoughtworks.rockveganfood.data.model.Dish

@Database(entities = [Dish::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dishDao(): DishDao
}