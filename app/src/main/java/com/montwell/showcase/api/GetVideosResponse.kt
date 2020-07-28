package com.montwell.showcase.api

import com.google.gson.annotations.SerializedName
import com.montwell.showcase.api.dto.VideoDTO

data class GetVideosResponse(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("results")val results: List<VideoDTO>
)