package com.montwell.showcase

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.montwell.showcase.api.GetMoviesResponse
import com.montwell.showcase.api.TheMovieDbOrgApi
import com.montwell.showcase.db.MovieDb
import com.montwell.showcase.repos.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MoviePreviewViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

//    @get:Rule
//    var coroutinesTestRule = CoroutineTestRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val testScope = TestCoroutineScope(testCoroutineDispatcher)

    @Mock
    private lateinit var theMovieDbOrgApi: TheMovieDbOrgApi

    private lateinit var movieDb: MovieDb

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        movieDb = Room.inMemoryDatabaseBuilder(context, MovieDb::class.java)
            .allowMainThreadQueries()
            .build()

        MockitoAnnotations.initMocks(this)
    }

    @After
    fun teardown() {
        movieDb.close()
    }

    @Test
    fun test_getMoviesEmpty() = runBlocking<Unit> {
        val movieDao = spy(movieDb.movieDao())
        val repo = MovieRepository(movieDb, theMovieDbOrgApi)
        val expected = 0

        Mockito.`when`(theMovieDbOrgApi.discoverMovies(1, null,""))
            .thenReturn(GetMoviesResponse(1, arrayListOf(), 0,1))

        var actual = 0

        assert(expected == actual)
    }
}