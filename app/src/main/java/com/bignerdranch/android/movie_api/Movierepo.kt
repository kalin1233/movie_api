package com.bignerdranch.android.movie_api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class Movierepo {
    private val movieApi: MovieApi

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        movieApi = retrofit.create(MovieApi::class.java)
    }

    suspend fun fetchMovies(searchText: String): List<MovieItems> =
        movieApi.fetchPhotos(searchText, API_KEY).search
}

