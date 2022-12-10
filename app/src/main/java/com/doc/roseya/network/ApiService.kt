package com.doc.roseya.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiService {

    companion object {
        private const val BASE_URL = "https://api.npoint.io/"
        const val BASE_URL_NEWS = "https://newsapi.org/v2/"
        fun getQuran(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
        fun getApiClient(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL_NEWS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}