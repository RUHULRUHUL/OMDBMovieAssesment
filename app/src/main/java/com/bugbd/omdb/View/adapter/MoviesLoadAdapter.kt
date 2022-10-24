package com.bugbd.omdb.View.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bugbd.omdb.databinding.ProductLoadStatBinding

class MoviesLoadAdapter(val retry: () -> Any) :
    LoadStateAdapter<MoviesLoadAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            ProductLoadStatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(private val binding: ProductLoadStatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.RetryBtn.setOnClickListener {
                retry.invoke()

            }
        }

        fun bind(loadState: LoadState) {

            binding.apply {
                binding.SHOWPROGRESS.isVisible = loadState is LoadState.Loading
                binding.RetryBtn.isVisible = loadState !is LoadState.Error
                binding.ErrorMsg.isVisible = loadState !is LoadState.Error
            }


        }

    }
}