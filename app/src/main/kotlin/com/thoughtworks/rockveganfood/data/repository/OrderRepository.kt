package com.thoughtworks.rockveganfood.data.repository

import com.thoughtworks.rockveganfood.data.localdatasource.MenuLocalDataSource
import com.thoughtworks.rockveganfood.data.model.Dish

class OrderRepository(private val localDataSource: MenuLocalDataSource) {

    suspend fun fetchMenu() = localDataSource.getAllDishes()

    suspend fun makeOrder(order: Map<Dish, Int>, dateTime: String) =
        localDataSource.createOrder(order, dateTime)
}