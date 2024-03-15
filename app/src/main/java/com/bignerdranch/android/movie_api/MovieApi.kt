package com.bignerdranch.android.movie_api


import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "874c86d"

interface MovieApi {
    @GET("/")
    suspend fun fetchPhotos(
        @Query("s") searchText: String,
        @Query("apikey") apiKey: String = API_KEY
    ): MResponse
}