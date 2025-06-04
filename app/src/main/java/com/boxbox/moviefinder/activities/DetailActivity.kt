package com.boxbox.moviefinder.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.boxbox.moviefinder.R
import com.boxbox.moviefinder.data.MovieDetailResponse
import com.boxbox.moviefinder.databinding.ActivityDetailBinding
import com.boxbox.moviefinder.utils.ApiService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var retrofit: Retrofit
    private lateinit var movie: MovieDetailResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val id = intent.getStringExtra(EXTRA_ID).orEmpty()
        retrofit = getRetrofit()
        getMovie(id)
    }

    private fun initUI() {
        with (binding) {
            Picasso.get().load(movie.poster).into(ivPoster)
            tvTitle.text = movie.title
            tvYear.text = movie.year
            tvRuntime.text = movie.runtime
            tvGenre.text = movie.genre
            tvDirector.text = movie.director
            tvCountry.text = movie.country
            tvPlot.text = movie.plot
        }
    }

    private fun getMovie(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit.create(ApiService::class.java).getMovieById("d0b8654", id)
            if (myResponse.body() != null) {
                runOnUiThread {
                    movie = myResponse.body()!!
                    initUI()
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
}