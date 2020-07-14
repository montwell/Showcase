package com.montwell.showcase.data.api

import com.montwell.showcase.data.dto.GetMoviesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TheMovieDbOrgService {

    @GET("discover/movie")
    suspend fun getMovies() : GetMoviesResponse

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): TheMovieDbOrgService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TheMovieDbOrgService::class.java)
        }
    }
}