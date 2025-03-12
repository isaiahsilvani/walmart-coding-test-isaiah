package com.example.walmartcodingtest.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartcodingtest.R
import com.example.walmartcodingtest.domain.models.Country

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private val data = mutableListOf<Country>()

    inner class CountryViewHolder(countryView: View) :RecyclerView.ViewHolder(countryView) {
        var name: TextView = countryView.findViewById(R.id.tvCountryName)
        var region: TextView = countryView.findViewById(R.id.tvRegion)
        var capital: TextView = countryView.findViewById(R.id.tvCapital)
        var code: TextView = countryView.findViewById(R.id.tvCode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.country_list_item, parent, false)
        return CountryViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        with (data[position]) {
            holder.name.text = this.name
            holder.region.text = this.region
            holder.capital.text = this.capital
            holder.code.text = this.code
        }
    }

    /**
     * Method to set the data and notify the adapter of the change
     */
    fun setData(countries: List<Country>?) {
        // remove data in order to display single country by using the same method
        if (data.isNotEmpty()) {
            val oldSize = data.size
            data.clear()
            notifyItemRangeRemoved(0, oldSize)
        }
        countries?.let {
            data.addAll(it)
        }
        notifyItemRangeChanged(0, data.size)
    }

}