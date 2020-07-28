package com.montwell.showcase.ui.movie_preview

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DiscoverMoviePreviewFragment() : MoviePreviewFragment() {

    private val args: DiscoverMoviePreviewFragmentArgs by navArgs()

    private val viewModel: MoviePreviewViewModel by activityViewModels()

    init {
        moviePreviewVariation = MoviePreviewVariation.DISCOVER
    }

    override fun getPagingData() {
        lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.discoverMovies(args.genreIds).collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
    }

    companion object {

        private val LOG_TAG = DiscoverMoviePreviewFragment::class.java.canonicalName

    }
}
