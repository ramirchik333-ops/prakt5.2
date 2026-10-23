package com.bignerdranch.android.prakt52

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val year: String?,
    val rating: String?,
    val poster: String?
)