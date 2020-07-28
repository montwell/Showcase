package com.montwell.showcase.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_genres")
data class MovieGenre(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val movieGenreId: Int,

    val name: String
)