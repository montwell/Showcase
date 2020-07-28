package com.montwell.showcase.api.dto

import com.google.gson.annotations.SerializedName
import com.montwell.showcase.db.entities.MovieGenre

data class MovieGenreDTO(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String
)

fun MovieGenreDTO.toMovieGenre() = MovieGenre(
    movieGenreId = id,
    name = name
)

