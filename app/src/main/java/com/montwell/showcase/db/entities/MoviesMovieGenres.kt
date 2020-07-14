package com.montwell.showcase.db.entities

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "movieGenreId"])
data class MoviesMovieGenres(

    val movieId: Int,

    val movieGenreId: Int
)