package com.bugbd.omdb.View.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bugbd.omdb.databinding.MovieRowItemBinding
import com.bugbd.omdb.View.MoviedetailsActivity
import com.bugbd.omdb.repository.model.Search
import com.bugbd.omdb.repository.utils.ConstantKey
import com.bumptech.glide.Glide

class MoviesAdapter(val context: Context) :
    PagingDataAdapter<Search, MoviesAdapter.ViewHolder>(comparator) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val search = getItem(position)

        search?.let {
            Glide.with(context).load(search.poster).into(holder.binding.Image)
        }

        holder.binding.Image.setOnClickListener {

            val intent = Intent(context, MoviedetailsActivity::class.java)
            intent.putExtra(ConstantKey.movieId, search?.imdbID)
            context.startActivity(intent)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MovieRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    class ViewHolder(val binding: MovieRowItemBinding) : RecyclerView.ViewHolder(binding.root)


    private companion object {
        private val comparator = object : DiffUtil.ItemCallback<Search>() {
            override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem == newItem
            }

        }
    }

}