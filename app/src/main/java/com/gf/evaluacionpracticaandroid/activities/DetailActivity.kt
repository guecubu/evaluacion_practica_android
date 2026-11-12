package com.gf.evaluacionpracticaandroid.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gf.evaluacionpracticaandroid.data.OmdbApi
import com.gf.evaluacionpracticaandroid.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val movieId = intent.getStringExtra("MOVIE_ID")
        if (!movieId.isNullOrEmpty()) {
            fetchMovieDetails(movieId)
        }
    }

    private fun fetchMovieDetails(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movie = OmdbApi.getInstance().getMovieDetails(id)
                withContext(Dispatchers.Main) {
                    binding.titleTextView.text = movie.title
                    binding.yearTextView.text = movie.year ?: "N/A"
                    binding.runtimeTextView.text = movie.runtime ?: "N/A"
                    binding.genreTextView.text = movie.genre ?: "N/A"
                    binding.directorTextView.text = movie.director ?: "N/A"
                    binding.countryTextView.text = movie.country ?: "N/A"
                    binding.actorsTextView.text = movie.actors ?: "N/A"
                    binding.plotTextView.text = movie.plot ?: ""

                    if (!movie.thumbnail.isNullOrEmpty() && movie.thumbnail != "N/A") {
                        Picasso.get()
                                .load(movie.thumbnail)
                                .placeholder(android.R.drawable.ic_menu_gallery)
                                .error(android.R.drawable.ic_menu_report_image)
                                .into(binding.posterImageView)
                    }
                }
            }
            catch (e: Exception) {
                Log.e("DetailActivity", "Error fetching movie details", e)
            }
        }
    }
}
