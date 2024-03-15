package com.bignerdranch.android.movie_api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class MResponse(
    @Json(name = "Search") val search: List<MovieItems>
)