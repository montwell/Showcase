package com.montwell.showcase.ui.main

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.montwell.showcase.R
import com.montwell.showcase.db.entities.MovieGenre
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var selectedMovieGenre: MovieGenre? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initSearch()
        initBrowse()
    }

    private fun initSearch() {

        buttonClearSearch.setOnClickListener {
            inputMovieTitleSearch.setText("")
        }

        buttonSearch.setOnClickListener { view ->
            viewModel.clearMovies()
            val query = inputMovieTitleSearch.text

            val direction = MainFragmentDirections
                .actionMainFragmentToSearchMoviePreviewFragment(query.toString())

            view.findNavController().navigate(direction)
        }

        inputMovieTitleSearch.setOnFocusChangeListener { view, hasFocus ->
            if(!hasFocus) {
                hideKeyboard(view)
            }
        }
    }

    private fun initBrowse() {
        viewModel.movieGenres.observe(viewLifecycleOwner) { movieGenres ->

            Log.d(LOG_TAG, "MovieGenres: $movieGenres")
            val movieGenreNames = ArrayList<String>()
            movieGenreNames.add("All")

            movieGenres.map { movieGenre ->
                movieGenreNames.add(movieGenre.name)
            }

            val movieGenreAdapter = ArrayAdapter(
                requireActivity(),
                R.layout.movie_genre_autocomplete_item,
                movieGenreNames
            )

            browseGenreAutoComplete.setAdapter(movieGenreAdapter)
        }

        viewModel.selectedMovieGenre.observe(viewLifecycleOwner) { movieGenre ->
            selectedMovieGenre = movieGenre
        }

        browseGenreAutoComplete.setOnItemClickListener { parent, _, position, _ ->
            val movieGenreName = parent.getItemAtPosition(position) as String
            Log.d(LOG_TAG, "MovieGenre Selected: $movieGenreName")
            viewModel.onMovieGenreSelected(movieGenreName)
        }

        buttonBrowse.setOnClickListener { view ->
            viewModel.clearMovies()
            val movieGenreId = selectedMovieGenre?.movieGenreId

            val movieGenreIds = if (movieGenreId != null) {
                "$movieGenreId"
            } else {
                null
            }

            val direction = MainFragmentDirections
                .actionMainFragmentToDiscoverMoviePreviewFragment(movieGenreIds)

            view.findNavController().navigate(direction)
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE)
                as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {

        private val LOG_TAG = MainFragment::class.java.canonicalName
    }

}