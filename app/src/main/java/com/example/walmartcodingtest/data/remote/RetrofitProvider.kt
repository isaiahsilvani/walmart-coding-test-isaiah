package com.example.walmartcodingtest.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitProvider {

    private val retrofitObject: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())

    fun getCountryApi(): CountriesApi =
        retrofitObject.baseUrl(CountriesApi.BASE_URL)
            .build()
            .create()

}