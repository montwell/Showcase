package com.montwell.showcase.db

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.montwell.showcase.db.dao.MovieDao
import com.montwell.showcase.db.dao.MovieGenreDao
import com.montwell.showcase.db.entities.Movie
import com.montwell.showcase.db.entities.MovieGenre
import com.montwell.showcase.db.entities.MoviesMovieGenre
import com.montwell.showcase.utilities.SharedPrefUtils

@Database(
    entities = [Movie::class, MovieGenre::class, MoviesMovieGenre::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun movieGenreDao(): MovieGenreDao

    companion object {

        fun create(context: Context, useInMemory: Boolean): MovieDb {

            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, MovieDb::class.java)
            } else {
                Room.databaseBuilder(context, MovieDb::class.java, "movie.db")
            }

            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}