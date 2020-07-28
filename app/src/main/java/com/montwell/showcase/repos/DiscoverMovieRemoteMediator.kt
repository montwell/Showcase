package com.montwell.showcase.repos

import androidx.paging.ExperimentalPagingApi
import com.montwell.showcase.api.TheMovieDbOrgApi
import com.montwell.showcase.api.GetMoviesResponse
import com.montwell.showcase.db.MovieDb

@OptIn(ExperimentalPagingApi::class)
class DiscoverMovieRemoteMediator(
    movieDb: MovieDb,
    private val movieApi: TheMovieDbOrgApi,
    genreIds: String?
) : MovieRemoteMediator<String?>(movieDb, genreIds) {

    companion object {
        private val LOG_TAG = DiscoverMovieRemoteMediator::class.java.canonicalName
    }

    override suspend fun getMovies(pageId: Int, query: String?): GetMoviesResponse {
        return movieApi.discoverMovies(pageId, query)
    }
}