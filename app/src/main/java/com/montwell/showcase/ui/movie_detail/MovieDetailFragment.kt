package com.montwell.showcase.ui.movie_detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.montwell.showcase.R
import com.montwell.showcase.api.dto.VideoDTO
import kotlinx.android.synthetic.main.movie_detail_fragment.*

class MovieDetailFragment : Fragment() {

    private val args: MovieDetailFragmentArgs by navArgs()

    private val viewModel: MovieDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDetails()
        initVideoList()
    }

    private fun initDetails() {
        movieDetailTitle.text = ""
        movieDetailOverview.text = ""
        movieDetailRating.text = ""
        movieDetailReleaseDate.text = ""

        val glideRequestManager = Glide.with(this)

        viewModel.movie.observe(viewLifecycleOwner) {movie ->

            if(movie != null) {

                if(movie.backdropPath != null) {
                    glideRequestManager.load(BACKDROP_BASE_URL + movie.backdropPath)
                        .centerCrop()
                        //TODO .placeholder()
                        .into(movieDetailBackdrop)
                } else {
                    glideRequestManager.clear(movieDetailBackdrop)
                }

                movieDetailTitle.text = movie.title
                movieDetailOverview.text = movie.overview
                movieDetailRating.text = movie.voteAverage.toString()
                movieDetailReleaseDate.text = movie.releaseDate

            }
        }

        viewModel.getMovieById(args.movieId)
    }

    private fun initVideoList() {
        videoList.layoutParams.height = 0

        viewModel.videos.observe(viewLifecycleOwner) {videos ->

            val filteredVideos = ArrayList<VideoDTO>()

            for(video in videos) {
                Log.d(LOG_TAG, "$video")
                if(video.site == "YouTube") {
                    filteredVideos.add(video)
                }
            }

            Log.d(LOG_TAG, "Filtered Videos: ${filteredVideos.size}")

            val videoAdapter = VideoAdapter(requireActivity(), R.layout.video, filteredVideos)
            updateListViewHeight(64, filteredVideos.size, videoList)
            videoList.adapter = videoAdapter
        }

        viewModel.getMovieVideos(args.movieId)
    }

    private fun updateListViewHeight(heightInDp: Int, count:Int, listView: ListView) {
        val density = resources.displayMetrics.density
        val layoutParams = listView.layoutParams
        val dividerHeight = listView.dividerHeight
        layoutParams.height =
            ((heightInDp * density * count) + (dividerHeight * (count - 1))).toInt()

        listView.layoutParams = layoutParams
    }

    companion object {

        private val LOG_TAG = MovieDetailFragment::class.java.canonicalName

        private const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780"

    }
}