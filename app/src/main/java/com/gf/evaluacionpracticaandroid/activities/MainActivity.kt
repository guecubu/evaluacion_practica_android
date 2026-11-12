package com.gf.evaluacionpracticaandroid.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.gf.evaluacionpracticaandroid.R
import com.gf.evaluacionpracticaandroid.adapters.MovieAdapter
import com.gf.evaluacionpracticaandroid.data.OmdbApi
import com.gf.evaluacionpracticaandroid.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupSearch()
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter { movie ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("MOVIE_ID", movie.id)
            }
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
    }

    private fun setupSearch() {
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            }
            else {
                false
            }
        }

        binding.searchTextInputLayout.setEndIconOnClickListener {
            performSearch()
        }
    }

    private fun performSearch() {
        val query = binding.searchEditText.text.toString().trim()
        if (query.isNotEmpty()) {
            fetchMovies(query)
        }
    }

    private fun fetchMovies(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = OmdbApi.getInstance().getMoviesBySearch(query)
                val movies = response.search ?: emptyList()

                withContext(Dispatchers.Main) {
                    adapter.submitList(movies)
                }
            }
            catch (e: Exception) {
                Log.e("MainActivity", "Error fetching movies", e)
            }
        }
    }
}
