package com.montwell.showcase.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["movieId", "movieGenreId"])
data class MoviesMovieGenres(

    val movieId: Int,

    val movieGenreId: Int
)