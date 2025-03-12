package com.example.walmartcodingtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walmartcodingtest.data.remote.RetrofitProvider
import com.example.walmartcodingtest.data.repositories.CountryRepoImpl
import com.example.walmartcodingtest.domain.usecases.GetCountries

class CountriesViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountriesViewModel::class.java)) {
            val getCountries = GetCountries(CountryRepoImpl(RetrofitProvider.getCountryApi()))
            return CountriesViewModel(getCountries) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
}