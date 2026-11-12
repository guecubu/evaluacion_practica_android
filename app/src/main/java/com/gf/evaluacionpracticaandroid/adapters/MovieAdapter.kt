package com.gf.evaluacionpracticaandroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gf.evaluacionpracticaandroid.data.Movie
import com.gf.evaluacionpracticaandroid.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(
        private val onItemClick: (Movie) -> Unit
                  ) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
                                              )
        return MovieViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class MovieViewHolder(
            private val binding: ItemMovieBinding,
            private val onItemClick: (Movie) -> Unit
                         ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.titleTextView.text = movie.title
            binding.yearTextView.text = movie.year

            if (!movie.thumbnail.isNullOrEmpty() && (movie.thumbnail != "N/A")) {
                Picasso.get()
                        .load(movie.thumbnail)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .error(android.R.drawable.ic_menu_report_image)
                        .into(binding.posterImageView)
            }
            else {
                binding.posterImageView.setImageResource(android.R.drawable.ic_menu_report_image)
            }

            binding.root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
