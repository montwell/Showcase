package com.montwell.showcase.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movies(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val movieId: Int,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    val adult: Boolean,

    val overview: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "original_title")
    val originalTitle: String,

    @ColumnInfo(name = "original_language")
    val originalLanguage: String,

    val title: String,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,

    val popularity: Double,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int,

    val video: String,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double
)
