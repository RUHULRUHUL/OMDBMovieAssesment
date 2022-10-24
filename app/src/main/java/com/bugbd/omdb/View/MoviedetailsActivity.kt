package com.bugbd.omdb.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bugbd.omdb.R
import com.bugbd.omdb.databinding.ActivityMoviedetailsBinding
import com.bugbd.omdb.ViewModel.MovieDetailsViewModel
import com.bugbd.omdb.repository.utils.ConstantKey
import com.bumptech.glide.Glide

class MoviedetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviedetailsBinding

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moviedetails)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_moviedetails)
        clickEvent()
        val movieId = intent.getStringExtra(ConstantKey.movieId)
        getMovieDetails(movieId)
    }

    private fun clickEvent() {
        binding.bacPressImg.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getMovieDetails(movieId: String?) {
        movieDetailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        movieId?.let {
            movieDetailsViewModel.getMovieDetails(movieId, ConstantKey.apiKey)
                .observe(this, Observer {
                    Glide.with(this).load(it.poster).into(binding.movieImg)
                    binding.movieTitleTxt.text = it.title
                    binding.ratingTxt.text = it.imdbRating
                    binding.releasedDateTxt.text = it.released
                    binding.voteTxt.text = it.imdbVotes
                    binding.movieDetailsTxt.text = it.plot

                })
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}