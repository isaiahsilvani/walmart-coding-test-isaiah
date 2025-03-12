package com.example.walmartcodingtest.domain.repositories

import com.example.walmartcodingtest.domain.models.Country
import com.example.walmartcodingtest.domain.models.Result

interface CountryRepo {

    suspend fun getCountries(): Result<List<Country>?>

}