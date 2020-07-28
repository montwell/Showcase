package com.montwell.showcase.api

import com.google.gson.annotations.SerializedName
import com.montwell.showcase.api.dto.MovieDTO

data class GetMoviesResponse (
    @field:SerializedName("page") val page : Int,
    @field:SerializedName("results") val results : List<MovieDTO>,
    @field:SerializedName("total_results") val totalResults: Int,
    @field:SerializedName("total_pages") val totalPages: Int
)