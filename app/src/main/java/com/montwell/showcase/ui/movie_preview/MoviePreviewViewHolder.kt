package com.montwell.showcase.ui.movie_preview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.montwell.showcase.R
import com.montwell.showcase.db.entities.Movie

class MoviePreviewViewHolder(
    view: View,
    private val glideRequestManager: RequestManager,
    moviePreviewVariation: MoviePreviewVariation
) : RecyclerView.ViewHolder(view) {

    private val poster: ImageView = view.findViewById(R.id.moviePreviewImage)
    private val title: TextView = view.findViewById(R.id.moviePreviewTitle)
    private val overview: TextView = view.findViewById(R.id.moviePreviewOverview)
    private var movie: Movie? = null

    init {
        view.setOnClickListener {
            movie?.movieId?.let {
                Log.d(LOG_TAG, "Movie preview clicked:  ${movie?.title}")

                val direction = when(moviePreviewVariation) {
                    MoviePreviewVariation.SEARCH -> {
                        SearchMoviePreviewFragmentDirections
                            .actionSearchMoviePreviewFragmentToMovieDetailFragment(it)
                    }
                    MoviePreviewVariation.DISCOVER -> {
                        DiscoverMoviePreviewFragmentDirections
                            .actionDiscoverMoviePreviewFragmentToMovieDetailFragment(it)
                    }
                }

                view.findNavController().navigate(direction)
            }
        }
    }

    fun bind(movie: Movie?) {
        this.movie = movie
        title.text = movie?.title ?: ""
        overview.text = movie?.overview ?: ""

        if (movie?.posterPath !== null) {
            glideRequestManager.load(POSTER_BASE_URL + movie.posterPath)
                .centerCrop()
                //TODO .placeholder()
                .into(poster)
        } else {
            glideRequestManager.clear(poster)
        }
    }

    companion object {

        private val LOG_TAG = MoviePreviewViewHolder::class.java.canonicalName

        private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185"

        fun create(
            parent: ViewGroup,
            glideRequestManager: RequestManager,
            moviePreviewVariation: MoviePreviewVariation
        ): MoviePreviewViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_preview, parent, false)

            return MoviePreviewViewHolder(
                view,
                glideRequestManager,
                moviePreviewVariation
            )
        }
    }
}