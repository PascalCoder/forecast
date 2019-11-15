package com.thepascal.forecast.models

import com.google.gson.annotations.SerializedName

data class City(
    val name: String = "",
    val country: String = ""
)

data class Forecast(
    var city: String = "",
    var temp: String = "",
    var condition: String = "",
    var day: String = "",
    var times: MutableList<String> = mutableListOf(),
    var temps: MutableList<Double> = mutableListOf()
)

data class ForecastList(
    @SerializedName("cnt")
    val count: Int = 0,
    val list: MutableList<com.thepascal.forecast.models.List> = mutableListOf(),
    val city: City = City()
)

data class List(
    @SerializedName("dt")
    val date: Int,
    val main: Main,
    val weather: MutableList<Weather>,
    @SerializedName("dt_txt")
    val dateTime: String
)

data class Main(
    val temp: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)