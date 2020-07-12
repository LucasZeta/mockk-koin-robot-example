package com.thoughtworks.rockveganfood.data.localdatasource

import com.thoughtworks.rockveganfood.data.dao.AppDatabase
import com.thoughtworks.rockveganfood.data.model.Dish

class MenuLocalDataSource(private val database: AppDatabase) {

    suspend fun getAllDishes() =
        database.dishDao().getAll()

    suspend fun createOrder(order: Map<Dish, Int>, dateTime: String) {}
}
