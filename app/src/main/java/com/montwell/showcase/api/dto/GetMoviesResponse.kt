package com.montwell.showcase.api.dto

import com.google.gson.annotations.SerializedName

data class GetMoviesResponse (
    @field:SerializedName("page") val page : Int,
    @field:SerializedName("results") val results : List<Movie>,
    @field:SerializedName("total_results") val totalResults: Int,
    @field:SerializedName("total_pages") val totalPages: Int
)