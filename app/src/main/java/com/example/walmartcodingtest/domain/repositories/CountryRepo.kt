package com.example.walmartcodingtest.domain.repositories

import com.example.walmartcodingtest.domain.models.Country

interface CountryRepo {

    suspend fun getCountries(): List<Country>?

}