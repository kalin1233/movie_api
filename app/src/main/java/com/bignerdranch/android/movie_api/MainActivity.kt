package com.bignerdranch.android.movie_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import coil.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var movieRepo: Movierepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText = findViewById<EditText>(R.id.editTextText)
        val searchButton = findViewById<Button>(R.id.search)

        // Create an instance of MovieRepo
        movieRepo = Movierepo()

        // Set onClick listener for the search button
        searchButton.setOnClickListener {
            val searchText = editText.text.toString()
            if (searchText.isNotEmpty()) {
                // Fetch data using CoroutineScope
                CoroutineScope(Dispatchers.IO).launch {
                    fetchData(searchText)
                }
            }
        }
    }

    private suspend fun fetchData(searchText: String) {
        try {
            // Perform API request using Retrofit
            val movies = movieRepo.fetchMovies(searchText)

            // Update UI with fetched data
            withContext(Dispatchers.Main) {
                if (movies.isNotEmpty()) {
                    val movieDetails = movies.map { "${it.title} (${it.year})" }
                    val movieTitlesString = movieDetails.joinToString("\n")
                    findViewById<TextView>(R.id.movieTitlesTextView).apply {
                        text = movieTitlesString
                        visibility = View.VISIBLE
                    }

                    // Display the poster of the first movie
                    val posterUrl = movies.first().posterUrl
                    findViewById<ImageView>(R.id.posterImageView).load(posterUrl)
                } else {
                    // Handle case when no movies are found
                    findViewById<TextView>(R.id.movieTitlesTextView).apply {
                        text = "No movies found."
                        visibility = View.VISIBLE
                    }
                    findViewById<ImageView>(R.id.posterImageView).setImageDrawable(null)
                }
            }
        } catch (e: Exception) {
            // Handle error (e.g., show a toast message)
            e.printStackTrace()
        }
    }
}




