package com.bugbd.omdb.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bugbd.omdb.databinding.FragmentHomeBinding
import com.bugbd.omdb.View.adapter.*
import com.bugbd.omdb.ViewModel.BatmanMovieViewModel
import com.bugbd.omdb.ViewModel.LatestMovieViewModel
import com.bugbd.omdb.ViewModel.SliderViewModel
import com.bugbd.omdb.repository.model.*
import com.bugbd.omdb.repository.utils.ConstantKey
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var batmanMovieAdapter: BatmanMovieAdapter
    private lateinit var latestMovieAdapter: LatestMovieAdapter

    private lateinit var sliders: ArrayList<Search>

    private lateinit var sliderViewModel: SliderViewModel
    private lateinit var batmanMovieViewModel: BatmanMovieViewModel
    private lateinit var latestMovieViewModel: LatestMovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //init ViewModel Object
        initViewModel()

        /*
        launch coroutines in MainThread then Create Background 3 async Thread for
        concurrently data get in OmDb Movie Server. when we get response concurrently
        then data send immediately Adapter for Ui  recommended data preview in RecyclerView

        1.getSliders
            use case:
                Five  movie  Banner Then we can show carousel Sliders(auto Sliders)

        2.Batman Movies
            use case:
                query title: "Batman"

        3.Latest Movies
            use case:
                query title: "movie" as you like
                year       : 2022
        note:
             response Observer of the MutableLiveData

        */

        lifecycleScope.launch {

            val sliderResponse: Deferred<MutableLiveData<MovieResponse>> = async {
                sliderViewModel.getSliders("movie", ConstantKey.apiKey)
            }

            val batmanResponse: Deferred<MutableLiveData<MovieResponse>> = async {
                batmanMovieViewModel.getBatmanMovies("Batman", ConstantKey.apiKey)
            }

            val latestMovieResponse: Deferred<MutableLiveData<MovieResponse>> = async {

                latestMovieViewModel.getLatestMovies("movie", ConstantKey.apiKey, "2022")
            }

            setSliderAdapter(sliderResponse)
            setBatManMovieAdapter(batmanResponse)
            setLatestMovieAdapter(latestMovieResponse)


        }


        return binding.root
    }

    private suspend fun setLatestMovieAdapter(latestMovieResponse: Deferred<MutableLiveData<MovieResponse>>) {
        latestMovieResponse.await().observe(viewLifecycleOwner, Observer {
            latestMovieAdapter = LatestMovieAdapter(it.searches, requireContext())
            binding.latestMovie.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.latestMovie.adapter = latestMovieAdapter

        })
    }

    private suspend fun setBatManMovieAdapter(batmanResponse: Deferred<MutableLiveData<MovieResponse>>) {
        batmanResponse.await().observe(viewLifecycleOwner, Observer {
            batmanMovieAdapter = BatmanMovieAdapter(it.searches, requireContext())
            binding.batmanMVRV.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.batmanMVRV.adapter = batmanMovieAdapter

        })
    }

    private suspend fun setSliderAdapter(sliderResponse: Deferred<MutableLiveData<MovieResponse>>) {
        sliders = arrayListOf()
        sliderResponse.await().observe(viewLifecycleOwner, Observer {

            sliders.clear()
            for (i in 0..it.searches.size) {
                sliders.add(it.searches[i])

                if (i == 4) {
                    break
                }
            }

            sliderAdapter = SliderAdapter(sliders, requireContext())
            binding.imageSlider.setSliderAdapter(sliderAdapter)
            binding.imageSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
            binding.imageSlider.scrollTimeInSec = 7
            binding.imageSlider.isAutoCycle = true
            binding.imageSlider.startAutoCycle()
        })
    }

    private fun initViewModel() {
        sliderViewModel = ViewModelProvider(this)[SliderViewModel::class.java]
        latestMovieViewModel = ViewModelProvider(this)[LatestMovieViewModel::class.java]
        batmanMovieViewModel = ViewModelProvider(this)[BatmanMovieViewModel::class.java]
    }


}