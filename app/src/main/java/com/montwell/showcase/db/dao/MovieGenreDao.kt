package com.montwell.showcase.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.montwell.showcase.db.entities.MovieGenre

@Dao
interface MovieGenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieGenres: List<MovieGenre>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieGenre: MovieGenre)

    @Query("SELECT * FROM movie_genres ORDER BY name ASC")
    fun movieGenres(): LiveData<List<MovieGenre>>

    @Query("SELECT * FROM movie_genres WHERE name LIKE :name ")
    suspend fun movieGenreByName(name: String): MovieGenre?

    @Query( "DELETE FROM movie_genres")
    suspend fun clearAll()
}