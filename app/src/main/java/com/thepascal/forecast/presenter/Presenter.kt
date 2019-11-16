package com.thepascal.forecast.presenter

import android.util.Log
import com.thepascal.forecast.Constants.BASE_URL
import com.thepascal.forecast.models.Forecast
import com.thepascal.forecast.models.ForecastList
import com.thepascal.forecast.models.WeatherApi
import com.thepascal.forecast.view.ViewContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class Presenter : PresenterContract {

    private val tag = Presenter::class.java.simpleName

    private lateinit var api: WeatherApi

    companion object PresenterConstants {
        var city: String = ""
        var condition: String = ""
        var temp: String = ""

        var lists: Array<ArrayList<com.thepascal.forecast.models.List>> = Array(5) { arrayListOf<com.thepascal.forecast.models.List>() }

        var forecastList: ForecastList = ForecastList()
    }


    lateinit var viewContract: ViewContract

    lateinit var day1Date: String
    val daysDate: Array<String> = Array(5) { "" }
    lateinit var days: MutableList<Forecast>


    override fun bindView(view: ViewContract) {
        viewContract = view
    }

    override fun initializeRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(WeatherApi::class.java)
    }

    override fun getForecasts(zipCode: String, units: String) {

        initializeArrayLists()

        api.getForecast(zipCode, units).enqueue(object : Callback<ForecastList> {

            override fun onResponse(call: Call<ForecastList>, response: Response<ForecastList>) {
                if (response.body() != null) {
                    days = mutableListOf()

                    (response.body())?.let {
                        forecastList = it

                        city = it.city.name
                        temp = "${it.list[0].main.temp}"
                        condition = it.list[0].weather[0].main

                        viewContract.addForecast(it)

                        Log.d(tag, "onResponse: ${it.list.size} temp: $temp")

                        day1Date = it.list[0].dateTime
                        daysDate[0] = day1Date.substring(0, day1Date.indexOf(" "))

                        var tempDate = it.list[0].dateTime
                        tempDate = tempDate.substring(0, tempDate.indexOf(" "))

                        var j = 1
                        for (i in 0 until it.list.size) {
                            var date = it.list[i].dateTime
                            date = date.substring(0, date.indexOf(" "))

                            if (tempDate == date) {
                                continue
                            } else {
                                if (j < daysDate.size) {
                                    daysDate[j] = date
                                    Log.d(tag, "onResponse: " + daysDate[j])
                                    j++
                                } else {
                                    break
                                }
                                tempDate = it.list[i].dateTime
                                tempDate = tempDate.substring(0, tempDate.indexOf(" "))
                            }
                        }

                        var l = 0
                        for (i in 0 until it.list.size) {
                            var date = it.list[i].dateTime
                            date = date.substring(0, date.indexOf(" "))

                            if (daysDate[l] == date) {
                                lists[l].add(it.list[i])
                                continue
                            } else {
                                l++
                                if (l < daysDate.size) {
                                    lists[l].add(it.list[i])
                                } else {
                                    break
                                }

                            }
                        }
                        Log.d(tag, "onResponse: Lists: ${lists[0].size}")
                    }
                } else {
                    viewContract.addForecast(ForecastList())
                }
            }

            override fun onFailure(call: Call<ForecastList>, t: Throwable) {
                Log.d(tag, "onFailure: Something went wrong! ${t.message}")
            }
        })
    }

    private fun initializeArrayLists() {
        for (i in 0 until lists.size) {
            lists[i] = ArrayList(8)
        }
    }

}