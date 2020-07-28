package com.montwell.showcase.ui.movie_detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.montwell.showcase.ServiceLocator
import com.montwell.showcase.api.dto.VideoDTO
import com.montwell.showcase.db.entities.Movie
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository by lazy {
        ServiceLocator.instance(application).getMovieRepository()
    }

    private val _movie = MutableLiveData<Movie?>()

    private val _videos = MutableLiveData<List<VideoDTO>>()

    val movie: LiveData<Movie?> = _movie

    val videos: LiveData<List<VideoDTO>> = _videos

    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            _movie.postValue(movieRepository.getMovieById(movieId))
        }
    }

    fun getMovieVideos(movieId: Int) {
        viewModelScope.launch {
            _videos.postValue(movieRepository.getMovieVideos(movieId))
        }
    }
}