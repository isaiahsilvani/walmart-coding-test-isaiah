package com.example.walmartcodingtest.data.remote

import com.example.walmartcodingtest.data.remote.dto.CountryDto
import retrofit2.Response
import retrofit2.http.GET

interface CountriesApi {

    companion object {
        const val BASE_URL = "https://gist.githubusercontent.com/"
        private const val END_POINT = "peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json"
    }

    @GET(END_POINT)
    suspend fun getCountries(): Response<List<CountryDto>>
}