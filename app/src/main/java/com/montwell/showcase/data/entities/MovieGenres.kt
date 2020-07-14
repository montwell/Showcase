package com.montwell.showcase.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_genres")
data class MovieGenres(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val movieGenreId: Int,

    val name: String
)