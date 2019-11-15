package com.thepascal.forecast.view

import com.thepascal.forecast.models.ForecastList

interface ViewContract {

    fun addForecast(dataSet: ForecastList)
    fun onError(errorMessage: String)
}