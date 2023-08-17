package com.bugbd.omdb.View.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bugbd.omdb.View.adapter.*
import com.bugbd.omdb.ViewModel.BatmanMovieViewModel
import com.bugbd.omdb.ViewModel.LatestMovieViewModel
import com.bugbd.omdb.ViewModel.SliderViewModel
import com.bugbd.omdb.databinding.FragmentHomeBinding
import com.bugbd.omdb.repository.model.*
import com.bugbd.omdb.repository.utils.ConstantKey
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClients
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.*
import nl.altindag.ssl.SSLFactory
import org.json.JSONObject


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
        //initSSL()
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //init ViewModel Object
        initViewModel()

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

/*
    fun toSSLHandshakeException(e: Throwable): SSLHandshakeException? {
        return if (e is SSLHandshakeException) {
            e as SSLHandshakeException
        } else SSLHandshakeException(e.message).initCause(e) as SSLHandshakeException
    }
*/


 /*   private fun initSSL() {





        try {
            Log.d("getDataFirebase", "start")
            val sslContext: SSLContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf(SSLExeption()), null)
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
            // Code that may throw SSLException
        } catch (e: SSLException) {
            e.printStackTrace()
            // Log the error message
            Log.e("getDataFirebase", "SSL Error: " + e.message)
        }
    }*/

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