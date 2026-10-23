package com.bignerdranch.android.prakt52

import androidx.room.*

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)

    @Query("SELECT * FROM movies")
    fun getAll(): List<MovieEntity>
}