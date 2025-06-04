package com.boxbox.moviefinder.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.moviefinder.data.MovieResponse
import com.boxbox.moviefinder.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MoviesViewHolder (view:View): RecyclerView.ViewHolder(view) {

    private val binding = ItemMovieBinding.bind(view)

    fun render(movieResponse: MovieResponse, onItemSelected :(String) -> Unit) {
        binding.tvTitle.text = movieResponse.title
        Picasso.get().load(movieResponse.poster).into(binding.ivPoster)
        binding.tvYear.text = movieResponse.year

        binding.root.setOnClickListener { onItemSelected(movieResponse.id)}
    }
}