package com.montwell.showcase.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbOrgApi {

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") pageId: Int = 1,
        @Query("with_genres") genreIds: String?,
        @Query("api_key") apiKey: String = API_KEY
    ): GetMoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("page") pageId: Int = 1,
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): GetMoviesResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = API_KEY
    ): GetMovieGenresResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): GetVideosResponse

    companion object {

        init {
            System.loadLibrary("api-keys")
        }

        private const val BASE_URL = "https://api.themoviedb.org/3/"

        private val API_KEY = getApiKey()

        fun create(): TheMovieDbOrgApi {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TheMovieDbOrgApi::class.java)
        }

        private external fun getApiKey(): String

    }
}