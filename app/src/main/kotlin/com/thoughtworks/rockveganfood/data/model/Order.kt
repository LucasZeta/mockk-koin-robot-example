package com.thoughtworks.rockveganfood.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dish(
    val title: String,
    val description: String,
    @PrimaryKey(autoGenerate = true) val uid: Long = 0
)
