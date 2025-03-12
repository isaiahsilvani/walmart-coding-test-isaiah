package com.example.walmartcodingtest.domain.usecases

import com.example.walmartcodingtest.domain.models.Country
import com.example.walmartcodingtest.domain.repositories.CountryRepo

class GetCountries(
    private val countriesRepo: CountryRepo
) {
    /**
     * Method to get the list of countries from the API
     */
    suspend operator fun invoke(): List<Country>? {
        return countriesRepo.getCountries()
    }
}