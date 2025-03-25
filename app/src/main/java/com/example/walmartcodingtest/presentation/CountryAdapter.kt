package com.example.walmartcodingtest.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartcodingtest.R
import com.example.walmartcodingtest.domain.models.Country

class CountryAdapter : ListAdapter<Country, CountryAdapter.CountryViewHolder>(CountryDiffCallback()) {

    inner class CountryViewHolder(countryView: View) : RecyclerView.ViewHolder(countryView) {
        val countryNameRegionAndCapital: TextView = countryView.findViewById(R.id.tvCountryNameRegionCapital)
        val code: TextView = countryView.findViewById(R.id.tvCountryCode)

        fun bind(country: Country) {
            val formattedText = "${country.name}, ${country.region}\n\n${country.capital}"
            countryNameRegionAndCapital.text = formattedText
            code.text = country.code
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.country_list_item, parent, false)
        return CountryViewHolder(v)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentCountry = getItem(position)
        holder.bind(currentCountry)
    }
}

class CountryDiffCallback : DiffUtil.ItemCallback<Country>() {
    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }
}