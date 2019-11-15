package com.thepascal.forecast.presenter

import com.thepascal.forecast.view.ViewContract

interface PresenterContract {

    fun bindView(view: ViewContract)
    fun initializeRetrofit()
    fun getForecasts(zipCode: String, units: String)
}