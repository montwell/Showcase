package com.montwell.showcase.api.dto

import com.google.gson.annotations.SerializedName

data class VideoDTO(
    @field:SerializedName("id") val id : String,
    @field:SerializedName("iso_639_1") val iso_639_1 : String,
    @field:SerializedName("iso_3166_1") val iso_3166_1 : String,
    @field:SerializedName("key") val key : String,
    @field:SerializedName("name") val name : String,
    @field:SerializedName("site") val site : String,
    @field:SerializedName("size") val size : Int,
    @field:SerializedName("type") val type : String
)

