package com.example.walmartcodingtest.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walmartcodingtest.domain.models.Country
import com.example.walmartcodingtest.domain.models.Result
import com.example.walmartcodingtest.domain.usecases.GetCountries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountriesViewModel(
    private val getCountries: GetCountries
): ViewModel() {

    private val _countriesState = MutableLiveData<List<Country>>()
    val countriesState get() = _countriesState

    // fetches a single country from the countries state
    private val _filteredCountriesState = MutableLiveData<List<Country>>()
    val filteredCountriesState get() = _filteredCountriesState

    // shows loading spinner
    private val _isLoading = MutableLiveData(false)
    val isLoading get() = _isLoading

    // shows error message
    private val _errorState = MutableSharedFlow<Exception>()
    val errorState: SharedFlow<Exception> = _errorState

    init {
        getCountriesList()
    }

    /**
     * Method to fetch the countries from the API and post response to LiveData
     */
    private fun getCountriesList() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCountries()
            withContext(Dispatchers.Main){
                _isLoading.value = false
                when (result) {
                    is Result.Success -> _countriesState.value = result.data
                    is Result.Error -> _errorState.emit(result.exception)
                }
            }
        }
    }

    fun filterCountriesByName(countryName: String?) {
        viewModelScope.launch(Dispatchers.Default) {
            countryName?.let { name ->
                _countriesState.value?.filter {
                    it.name.lowercase().startsWith(name.lowercase())
                }?.let { country ->
                    _filteredCountriesState.postValue(country)
                }
            }
        }
    }

}