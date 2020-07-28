package com.montwell.showcase.api.dto

import com.google.gson.annotations.SerializedName
import com.montwell.showcase.db.entities.Movie

data class MovieDTO(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("poster_path") val posterPath: String?,
    @field:SerializedName("adult") val adult: Boolean,
    @field:SerializedName("overview") val overview: String,
    @field:SerializedName("release_date") val releaseDate: String?,
    @field:SerializedName("original_title") val originalTitle: String,
    @field:SerializedName("original_language") val originalLanguage: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("backdrop_path") val backdropPath: String?,
    @field:SerializedName("popularity") val popularity: Double,
    @field:SerializedName("vote_count") val voteCount: Int,
    @field:SerializedName("video") val video: Boolean,
    @field:SerializedName("vote_average") val voteAverage: Double
)

fun MovieDTO.toMovie() = Movie(
    movieId = id,
    posterPath = posterPath,
    adult = adult,
    overview = overview,
    releaseDate = releaseDate,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    title = title,
    backdropPath = backdropPath,
    popularity = popularity,
    voteCount = voteCount,
    video = video,
    voteAverage = voteAverage
)