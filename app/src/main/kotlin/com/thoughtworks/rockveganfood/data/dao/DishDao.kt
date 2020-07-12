package com.thoughtworks.rockveganfood.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.thoughtworks.rockveganfood.data.model.Dish

@Dao
interface DishDao {

    @Query("SELECT * FROM dish")
    suspend fun getAll(): List<Dish>

    @Insert
    fun insert(vararg dishes: Dish)
}
