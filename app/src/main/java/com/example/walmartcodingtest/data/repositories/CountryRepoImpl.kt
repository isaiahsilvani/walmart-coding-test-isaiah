package com.example.walmartcodingtest.data.repositories

import com.example.walmartcodingtest.data.remote.CountriesApi
import com.example.walmartcodingtest.domain.models.Country
import com.example.walmartcodingtest.domain.repositories.CountryRepo
import com.example.walmartcodingtest.domain.models.Result

class CountryRepoImpl(
    private val countriesApi: CountriesApi
) : CountryRepo {

    override suspend fun getCountries(): Result<List<Country>?> {
        return try {
            val response = countriesApi.getCountries()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.Success(body.map { it.toCountry() })
                } ?: Result.Error(Exception("Response body is null"))
            } else {
                Result.Error(Exception("Response not successful: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}