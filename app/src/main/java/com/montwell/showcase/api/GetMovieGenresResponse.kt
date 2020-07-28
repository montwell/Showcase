package com.montwell.showcase.api

import com.google.gson.annotations.SerializedName
import com.montwell.showcase.api.dto.MovieGenreDTO

data class GetMovieGenresResponse(
    @field:SerializedName("genres") val results : List<MovieGenreDTO>
)
