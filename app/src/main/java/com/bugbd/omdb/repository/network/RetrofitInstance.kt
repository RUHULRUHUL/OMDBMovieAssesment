package com.bugbd.omdb.repository.network

import com.bugbd.omdb.repository.utils.ConstantKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitInstance {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {

        val interceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()


        return Retrofit.Builder()
            .baseUrl(ConstantKey.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun getApi(): ApiServices {
        return getRetrofit().create(ApiServices::class.java)
    }


}