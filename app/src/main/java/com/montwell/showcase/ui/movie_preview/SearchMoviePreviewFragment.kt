package com.montwell.showcase.ui.movie_preview

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchMoviePreviewFragment() : MoviePreviewFragment() {

    private val args: SearchMoviePreviewFragmentArgs by navArgs()

    private val viewModel: MoviePreviewViewModel by activityViewModels()

    init {
        moviePreviewVariation = MoviePreviewVariation.SEARCH
    }

    override fun getPagingData() {
        lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.searchMovies(args.query).collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
    }

    companion object {

        private val LOG_TAG = SearchMoviePreviewFragment::class.java.canonicalName

    }
}
