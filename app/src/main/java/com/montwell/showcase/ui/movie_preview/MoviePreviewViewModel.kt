package com.montwell.showcase.ui.movie_preview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.montwell.showcase.ServiceLocator
import com.montwell.showcase.db.entities.Movie
import com.montwell.showcase.db.entities.MovieGenre
import kotlinx.coroutines.flow.*

class MoviePreviewViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository by lazy {
        ServiceLocator.instance(application).getMovieRepository()
    }

    private var currentSearchResult: Flow<PagingData<Movie>>? = null

    fun discoverMovies(genreIds: String?): Flow<PagingData<Movie>> {
        val newResult: Flow<PagingData<Movie>> =
            movieRepository.discoverMovies(genreIds).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    fun searchMovies(query: String): Flow<PagingData<Movie>> {
        val newResult: Flow<PagingData<Movie>> =
            movieRepository.searchMovies(query).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}
