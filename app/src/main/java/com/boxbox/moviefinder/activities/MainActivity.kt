package com.boxbox.moviefinder.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.boxbox.moviefinder.R
import com.boxbox.moviefinder.activities.DetailActivity.Companion.EXTRA_ID
import com.boxbox.moviefinder.adapter.MoviesAdapter
import com.boxbox.moviefinder.data.MovieDetailResponse
import com.boxbox.moviefinder.data.MovieResponse
import com.boxbox.moviefinder.databinding.ActivityMainBinding
import com.boxbox.moviefinder.utils.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI() {
        binding.svMovies.setOnQueryTextListener(object: SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName("d0b8654", query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })

        moviesAdapter = MoviesAdapter { movieId -> onItemSelected(movieId)}
        binding.rvMovies.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
        }

    }

    private fun searchByName(apiKey: String, query: String) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit.create(ApiService::class.java)
                .getMovies(apiKey, query) //

            if (myResponse.isSuccessful) {
                val movies = myResponse.body()?.movies ?: emptyList()

                runOnUiThread {
                    moviesAdapter.updateList(movies)
                    binding.progressBar.isVisible = false
                }
            } else {
                runOnUiThread {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }


    private fun getRetrofit() : Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun onItemSelected(id: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }
}