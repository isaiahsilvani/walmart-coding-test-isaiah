package com.example.walmartcodingtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walmartcodingtest.domain.models.Country
import com.example.walmartcodingtest.domain.models.Result
import com.example.walmartcodingtest.domain.usecases.GetCountries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountriesViewModel(
    private val getCountries: GetCountries
): ViewModel() {

    private val _countriesState = MutableLiveData<List<Country>>()
    val countriesState get() = _countriesState

    // fetches a single country from the countries state
    private val _selectedCountryState = MutableLiveData<List<Country>>()
    val selectedCountryState get() = _selectedCountryState

    // shows loading spinner
    private val _isLoading = MutableLiveData(false)
    val isLoading get() = _isLoading

    // shows error message
    private val _errorState = MutableLiveData<Exception>()
    val errorState get() = _errorState

    /**
     * Method to fetch the countries from the API and post response to LiveData
     */
    fun getCountriesList() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            getCountries().let { result ->
                _isLoading.postValue(false)
                when (result) {
                    is Result.Success -> _countriesState.postValue(result.data)
                    is Result.Error -> _errorState.postValue(result.exception)
                }
            }
        }
    }

    fun filterCountriesByName(countryName: String?) {
        viewModelScope.launch(Dispatchers.Default) {
            countryName?.let { name ->
                viewModelScope.launch(Dispatchers.Default) {
                    _countriesState.value?.filter {
                        it.name.lowercase().startsWith(name.lowercase())
                    }?.let { country ->
                        _selectedCountryState.postValue(country)
                    }
                }
            }
        }
    }

}