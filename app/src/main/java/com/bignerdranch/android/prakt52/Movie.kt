package com.bignerdranch.android.prakt52

data class Movie(
    val kinopoiskId: Int,
    val nameRu: String?,
    val year: String?,
    val rating: String?,
    val posterUrl: String?
)

data class MovieResponse(
    val films: List<Movie>
)