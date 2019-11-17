package com.thepascal.forecast.view

import com.thepascal.forecast.models.ForecastsData

interface ViewContract {

    fun addForecast(dataSet: ForecastsData)
    fun onError(errorMessage: String)
}