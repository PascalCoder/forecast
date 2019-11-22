package com.thepascal.forecast.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thepascal.forecast.R
import com.thepascal.forecast.models.ForecastsData
import com.thepascal.forecast.view.CustomViewHolder

class CustomAdapter(private var dataSet: ForecastsData) : RecyclerView.Adapter<CustomViewHolder>() {

    //private val tag = CustomAdapter::class.java.simpleName

    init {
        dataSet = Presenter.forecastsData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)

        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(Presenter.forecasts[position], position)
    }

    override fun getItemCount(): Int = 5 //number of days forecast

}