package com.boxbox.moviefinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.moviefinder.R
import com.boxbox.moviefinder.data.MovieResponse

class MoviesAdapter (
    var moviesList: List<MovieResponse> = emptyList(),
    private val onItemSelected: (String) -> Unit
): RecyclerView.Adapter<MoviesViewHolder>() {

    fun updateList(moviesList: List<MovieResponse>?) {
        if (moviesList != null) {
            this.moviesList = moviesList
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.render(moviesList[position], onItemSelected)
    }
}