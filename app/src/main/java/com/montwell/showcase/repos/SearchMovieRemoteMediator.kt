package com.montwell.showcase.repos

import androidx.paging.ExperimentalPagingApi
import com.montwell.showcase.api.TheMovieDbOrgApi
import com.montwell.showcase.api.GetMoviesResponse
import com.montwell.showcase.db.MovieDb

@OptIn(ExperimentalPagingApi::class)
class SearchMovieRemoteMediator(
    movieDb: MovieDb,
    private val movieApi: TheMovieDbOrgApi,
    searchQuery: String
) : MovieRemoteMediator<String>(movieDb, searchQuery) {

    override suspend fun getMovies(pageId: Int, query: String): GetMoviesResponse {
        return movieApi.searchMovies(pageId, query)
    }

    companion object {
        private val LOG_TAG = SearchMovieRemoteMediator::class.java.canonicalName
    }
}