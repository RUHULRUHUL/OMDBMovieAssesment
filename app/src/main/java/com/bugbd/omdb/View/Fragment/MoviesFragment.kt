package com.bugbd.omdb.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bugbd.omdb.databinding.FragmentCategoriesBinding
import com.bugbd.omdb.View.adapter.MoviesAdapter
import com.bugbd.omdb.View.adapter.MoviesLoadAdapter
import com.bugbd.omdb.ViewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)

        getMovies()

        return binding.root
    }

    private fun getMovies() {

        moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        moviesAdapter = MoviesAdapter(requireContext())
        binding.moviesRV.layoutManager =
            GridLayoutManager(context, 2)
        binding.moviesRV.setHasFixedSize(true)
        binding.moviesRV.adapter = moviesAdapter
        moviesAdapter.withLoadStateHeaderAndFooter(
            header = MoviesLoadAdapter { moviesAdapter.retry() },
            footer = MoviesLoadAdapter { moviesAdapter.retry() }
        )

        moviesViewModel.list.observe(requireActivity(), Observer {
            it?.let {
                moviesAdapter.submitData(lifecycle, it)
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}