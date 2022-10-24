package com.bugbd.omdb.View.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bugbd.omdb.databinding.SliderRowItemBinding
import com.bugbd.omdb.View.MoviedetailsActivity
import com.bugbd.omdb.repository.model.Search
import com.bugbd.omdb.repository.utils.ConstantKey
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(
    private val sliders: ArrayList<Search>,
    val context: Context
) : SliderViewAdapter<SliderAdapter.ViewHolder>() {


    override fun getCount(): Int {
        return sliders.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        return ViewHolder(
            SliderRowItemBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
            )
        )


    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val slider = sliders[position]

        holder?.binding?.SliderImg?.let {
            Glide.with(context).load(slider.poster).into(
                it
            )
        }

        holder?.binding?.SliderImg?.setOnClickListener {
            val intent = Intent(context, MoviedetailsActivity::class.java)
            intent.putExtra(ConstantKey.movieId, slider.imdbID)
            context.startActivity(intent)
        }

    }

    class ViewHolder(val binding: SliderRowItemBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {
    }
}