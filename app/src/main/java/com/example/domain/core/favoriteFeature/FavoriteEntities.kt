package com.example.domain.core.favoriteFeature

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey
    val id: Long,
    val title: String,
    val handle: String,
    val price :Int,
    val image: String,
    val page: Char,
    val count: Int
)