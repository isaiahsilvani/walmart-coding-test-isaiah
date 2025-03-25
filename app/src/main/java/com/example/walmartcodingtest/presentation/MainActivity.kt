package com.example.walmartcodingtest.presentation

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartcodingtest.R
import com.example.walmartcodingtest.databinding.ActivityMainBinding
import com.example.walmartcodingtest.presentation.viewmodel.CountriesViewModel
import com.example.walmartcodingtest.presentation.viewmodel.CountriesViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var countryAdapter: CountryAdapter

    private val viewModel: CountriesViewModel by viewModels {
        CountriesViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity()
        initViews()
        initRecyclerView()
        initObservers()
    }

    private fun initObservers() {
        with (viewModel) {
            val context = this@MainActivity
            countriesState.observe(context) { countries ->
                countryAdapter.submitList(countries)
                binding.tvEmptyCountriesList.isVisible = countries.isEmpty()
            }
            filteredCountriesState.observe(context) { countries ->
                countryAdapter.submitList(countries)
                binding.tvEmptyCountriesList.isVisible = countries.isEmpty()
            }
            isLoading.observe(context) { isLoading ->
                binding.loadingSpinner.isVisible = isLoading
            }
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    errorState.collect { error ->
                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    }
                }
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
                    countryAdapter.submitList(viewModel.countriesState.value)
                } else {
                    viewModel.filterCountriesByName(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    countryAdapter.submitList(viewModel.countriesState.value)
                } else {
                    viewModel.filterCountriesByName(newText)
                }
                return true
            }
        })
    }

    private fun initActivity() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}