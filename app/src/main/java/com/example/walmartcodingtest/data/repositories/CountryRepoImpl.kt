package com.example.walmartcodingtest.data.repositories

import android.util.Log
import com.example.walmartcodingtest.data.remote.CountriesApi
import com.example.walmartcodingtest.domain.models.Country
import com.example.walmartcodingtest.domain.repositories.CountryRepo

class CountryRepoImpl(
    private val countriesApi: CountriesApi
) : CountryRepo {

    override suspend fun getCountries(): List<Country>? {
        return try {
            countriesApi.getCountries().let { res ->
                if (res.isSuccessful) {
                    res.body()?.map { it.toCountry() }
                } else {
                    Log.e("ERROR", "Response not successful")
                    emptyList()
                }
            }
        } catch (e: Exception) {
            Log.e("ERROR", "There was an error fetching the countries: ${e.message}")
            emptyList()
        }
    }

}