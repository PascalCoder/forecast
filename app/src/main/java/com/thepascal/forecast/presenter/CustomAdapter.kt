package com.thepascal.forecast.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thepascal.forecast.Constants.VIEW_HOLDER_ERROR
import com.thepascal.forecast.Constants.VIEW_HOLDER_OK
import com.thepascal.forecast.R
import com.thepascal.forecast.models.ForecastsData
import com.thepascal.forecast.view.BaseViewHolder
import com.thepascal.forecast.view.CustomViewHolder
import com.thepascal.forecast.view.ErrorViewHolder

class CustomAdapter(private var dataSet: ForecastsData) : RecyclerView.Adapter<BaseViewHolder>() {

    //private val tag = CustomAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =

        when (viewType) {
            VIEW_HOLDER_OK -> CustomViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_layout, parent, false)
            )
            else -> ErrorViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_error_layout, parent, false)
            )

        }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        when (holder) {
            is CustomViewHolder -> holder.bind(Presenter.forecasts[position], position)
            is ErrorViewHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return when {
            dataSet.list.size > 0 -> 5 //number of days forecast
            else -> 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            dataSet.list.size > 0 -> VIEW_HOLDER_OK
            else -> VIEW_HOLDER_ERROR
        }
    }

}