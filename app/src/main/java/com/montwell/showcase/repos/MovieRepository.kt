package com.montwell.showcase.repos

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.montwell.showcase.api.TheMovieDbOrgApi
import com.montwell.showcase.api.dto.VideoDTO
import com.montwell.showcase.api.dto.toMovieGenre
import com.montwell.showcase.db.MovieDb
import com.montwell.showcase.db.entities.Movie
import com.montwell.showcase.db.entities.MovieGenre
import com.montwell.showcase.utilities.SharedPrefUtils
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun searchMovies(query: String): Flow<PagingData<Movie>>

    fun discoverMovies(genreIds: String?): Flow<PagingData<Movie>>

    suspend fun fetchMovieGenres(context: Context): Result<Boolean>

    suspend fun clearAllMovies()

    suspend fun getMovieGenreByName(name: String): MovieGenre?

    suspend fun getMovieById(movieId: Int): Movie?

    suspend fun getMovieVideos(movieId: Int): List<VideoDTO>
}

class MovieRepository(
    private val movieDb: MovieDb,
    private val movieApi: TheMovieDbOrgApi
) : IMovieRepository {

    val movieGenres = movieDb.movieGenreDao().movieGenres()

    override fun searchMovies(query: String) = Pager(
        config = PagingConfig(PAGE_SIZE),
        remoteMediator = SearchMovieRemoteMediator(movieDb, movieApi, query)
    ) {
        movieDb.movieDao().getMovies()
    }.flow

    override fun discoverMovies(genreIds: String?) = Pager(
        config = PagingConfig(PAGE_SIZE),
        remoteMediator = DiscoverMovieRemoteMediator(movieDb, movieApi, genreIds)
    ) {
        movieDb.movieDao().getMovies()
    }.flow

    override suspend fun fetchMovieGenres(context: Context): Result<Boolean> {
        return try {

            val currentTimestampInMillis = System.currentTimeMillis()

            val lastUpdateTimestampInMillis = SharedPrefUtils.getLong(
                context,
                PREF_LAST_MOVIE_GENRE_UPDATE,
                0L
            )


            if (shouldUpdateMovieGenres(
                    currentTimestampInMillis,
                    lastUpdateTimestampInMillis,
                    MOVIE_GENRE_EXPIRATION_IN_MILLIS
                )) {
                movieDb.movieGenreDao().clearAll()

                val response = movieApi.getMovieGenres()

                val movieGenres = response.results.map {
                    it.toMovieGenre()
                }

                movieDb.movieGenreDao().insertAll(movieGenres)
            }

            Result.Success(true)

        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun clearAllMovies() {
        movieDb.movieDao().clearAll()
    }

    override suspend fun getMovieGenreByName(name: String): MovieGenre? {
        return movieDb.movieGenreDao().movieGenreByName(name)
    }

    override suspend fun getMovieById(movieId: Int): Movie? {
        return movieDb.movieDao().getMovieById(movieId)
    }

    override suspend fun getMovieVideos(movieId: Int): List<VideoDTO> {
        return movieApi.getMovieVideos(movieId).results
    }

    private fun shouldUpdateMovieGenres(
        currentTime: Long,
        lastUpdate: Long,
        timeToExpire: Long
    ): Boolean {
        return (currentTime - timeToExpire > lastUpdate)
    }

    companion object {

        private const val PAGE_SIZE = 20

        private const val PREF_LAST_MOVIE_GENRE_UPDATE = "pref_last_movie_genre_update"

        private const val MOVIE_GENRE_EXPIRATION_IN_MILLIS = 1000L * 60L * 60L * 24L // 24 hours

    }
}