package com.example.walmartcodingtest.data.remote.dto

import com.example.walmartcodingtest.domain.models.Country

data class CountryDto(
    val capital: String,
    val code: String,
    val currency: CurrencyDto,
    val flag: String,
    val language: LanguageDto,
    val name: String,
    val region: String
) {
    fun toCountry() = Country(name, region, code, capital.ifBlank { "N/A" })
}