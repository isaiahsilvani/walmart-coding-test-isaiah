package com.example.walmartcodingtest.presentation

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartcodingtest.R
import com.example.walmartcodingtest.databinding.ActivityMainBinding
import com.example.walmartcodingtest.presentation.viewmodel.CountriesViewModel
import com.example.walmartcodingtest.presentation.viewmodel.CountriesViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CountriesViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity()
        initViews()
        initRecyclerView()
        initObservers()
        viewModel.getCountriesList()
    }

    private fun initObservers() {
        with (viewModel) {
            countriesState.observe(this@MainActivity) { countries ->
                countryAdapter.setData(countries)
            }
            selectedCountryState.observe(this@MainActivity) { country ->
                countryAdapter.setData(country)
            }
            isLoading.observe(this@MainActivity) { isLoading ->
                binding.loadingSpinner.isVisible = isLoading
            }
            errorState.observe(this@MainActivity) { error ->
                Toast.makeText(this@MainActivity.baseContext, error.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initRecyclerView() {
        countryAdapter = CountryAdapter()
        binding.rvCountries.apply {
            adapter = countryAdapter
            (adapter as RecyclerView.Adapter).stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                LinearLayoutManager(context)
            } else GridLayoutManager(context, 2)
        }
    }

    private fun initViews() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrBlank()) {
                    countryAdapter.setData(viewModel.countriesState.value)
                } else {
                    viewModel.filterCountriesByName(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    countryAdapter.setData(viewModel.countriesState.value)
                } else {
                    viewModel.filterCountriesByName(newText)
                }
                return true
            }
        })
    }

    private fun initActivity() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = CountriesViewModelFactory().create(CountriesViewModel::class.java)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}