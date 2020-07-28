package com.montwell.showcase.db.entities

import androidx.room.Entity

@Entity(primaryKeys = ["id", "movieGenreId"])
data class MoviesMovieGenre(

    val id: Int,

    val movieGenreId: Int
)