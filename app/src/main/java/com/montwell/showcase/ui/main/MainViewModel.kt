package com.montwell.showcase.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.montwell.showcase.ServiceLocator
import com.montwell.showcase.db.entities.MovieGenre
import com.montwell.showcase.repos.Result
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository by lazy {
        ServiceLocator.instance(application).getMovieRepository()
    }

    private val _isLoading = MutableLiveData<Boolean>()

    private val _onError = MutableLiveData<Exception>()

    private val _selectedMovieGenre = MutableLiveData<MovieGenre>()

    val isLoading: LiveData<Boolean> = _isLoading

    val onError: LiveData<Exception> = _onError

    val selectedMovieGenre: LiveData<MovieGenre> = _selectedMovieGenre

    val movieGenres: LiveData<List<MovieGenre>> = movieRepository.movieGenres

    init {
        viewModelScope.launch {
            _isLoading.postValue(true)

            val movieGenres = movieRepository.fetchMovieGenres(application)
            _isLoading.postValue(false)

            when (movieGenres) {
                is Result.Error -> {
                    _onError.postValue(movieGenres.exception)
                }
            }
        }

        onError.observeForever {
            Log.e(LOG_TAG, "Error: ${it.localizedMessage}")
        }

    }

    fun onMovieGenreSelected(name: String) {
        viewModelScope.launch {
            val movieGenre = movieRepository.getMovieGenreByName(name)
            _selectedMovieGenre.postValue(movieGenre)
        }
    }

    fun clearMovies(){
        viewModelScope.launch {
            movieRepository.clearAllMovies()
        }
    }

    companion object {

        private val LOG_TAG = MainViewModel::class.java.canonicalName
    }
}