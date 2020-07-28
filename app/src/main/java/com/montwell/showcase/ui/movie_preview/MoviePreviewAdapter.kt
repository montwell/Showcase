package com.montwell.showcase.ui.movie_preview

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.montwell.showcase.db.entities.Movie

class MoviePreviewAdapter(
    private val moviePreviewVariation: MoviePreviewVariation,
    private val glideRequestManager: RequestManager
) : PagingDataAdapter<Movie, MoviePreviewViewHolder>(MOVIE_COMPARATOR) {

    override fun onBindViewHolder(moviePreviewViewHolder: MoviePreviewViewHolder, position: Int) {
        moviePreviewViewHolder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePreviewViewHolder {
        return MoviePreviewViewHolder.create(parent, glideRequestManager, moviePreviewVariation)
    }

    companion object {

        private val LOG_TAG = MoviePreviewAdapter::class.java.canonicalName

        val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.movieId == newItem.movieId


            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}