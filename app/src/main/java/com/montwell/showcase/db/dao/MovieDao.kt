package com.montwell.showcase.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.montwell.showcase.db.entities.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("SELECT * FROM movies")
    fun getMovies(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movies WHERE movieId LIKE :movieId")
    suspend fun getMovieById(movieId: Int): Movie?

    @Query( "DELETE FROM movies")
    suspend fun clearAll()

}