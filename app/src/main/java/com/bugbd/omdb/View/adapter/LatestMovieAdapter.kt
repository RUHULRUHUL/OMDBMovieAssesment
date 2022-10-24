package com.bugbd.omdb.View.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bugbd.omdb.databinding.MovieRowItemBinding
import com.bugbd.omdb.View.MoviedetailsActivity
import com.bugbd.omdb.repository.model.Search
import com.bugbd.omdb.repository.utils.ConstantKey
import com.bumptech.glide.Glide

class LatestMovieAdapter(
    private var latestMovies: List<Search>,
    var context: Context

) : RecyclerView.Adapter<LatestMovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            MovieRowItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val search = latestMovies[position]
        Glide.with(context).load(search.poster).into(holder.binding.Image)


        holder.binding.Image.setOnClickListener {
            val intent = Intent(context, MoviedetailsActivity::class.java)
            intent.putExtra(ConstantKey.movieId, search.imdbID)
            context.startActivity(intent)
        }



    }

    override fun getItemCount(): Int {
        return latestMovies.size
    }

    class ViewHolder(val binding: MovieRowItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}


