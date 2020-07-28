package com.montwell.showcase.ui.movie_preview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.montwell.showcase.R
import kotlinx.android.synthetic.main.movie_preview_fragment.*

abstract class MoviePreviewFragment() : Fragment() {

    protected lateinit var movieAdapter: MoviePreviewAdapter

    protected lateinit var moviePreviewVariation: MoviePreviewVariation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.movie_preview_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        val glideRequestManager = Glide.with(this)

        movieAdapter =
            MoviePreviewAdapter(moviePreviewVariation, glideRequestManager)

        movieRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter
        }

        getPagingData()
    }

    abstract fun getPagingData()
}
