package com.montwell.showcase.repos

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.montwell.showcase.api.GetMoviesResponse
import com.montwell.showcase.api.dto.toMovie
import com.montwell.showcase.db.MovieDb
import com.montwell.showcase.db.entities.Movie
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
abstract class MovieRemoteMediator<T>(
    private val movieDb: MovieDb,
    private val query: T
) : RemoteMediator<Int, Movie>() {

    private val movieDao = movieDb.movieDao()

    private var previousPage: Int? = null

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {

        return try {

            val nextPage = when(loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {

                    Log.d(LOG_TAG, "APPENDING DATA")

                    val previousPage = this.previousPage
                    previousPage ?: return MediatorResult.Success(endOfPaginationReached = true)
                    previousPage + 1
                }
            }

            val response = getMovies(nextPage?: 1, query)

            movieDb.withTransaction {

                if(loadType == LoadType.REFRESH) {
                    movieDao.clearAll()
                }

                previousPage = response.page

                val movies = response.results.map {
                    it.toMovie()
                }

                Log.d(LOG_TAG, "Movies: $movies")

                movieDao.insertAll(movies)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.page == response.totalPages
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }

    abstract suspend fun getMovies(pageId:Int, query: T): GetMoviesResponse

    companion object {
        private val LOG_TAG = MovieRemoteMediator::class.java.canonicalName
    }
}