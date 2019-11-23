package com.thepascal.forecast

object Constants {
    const val BASE_URL: String = "http://api.openweathermap.org/"
    const val IMAGE_URL = "http://openweathermap.org/img/w/"
    const val IMAGE_PATH = ".png"

    const val DEGREE = "°"
    const val ANTE_MERIDIAN = "AM"
    const val POST_MERIDIAN = "PM"

    const val DEFAULT_ZIP_CODE = "30071" //"55431"
    const val DEFAULT_SYSTEM = "imperial"
    const val METRIC_SYSTEM = "metric"

    const val UPDATE_REQUEST: Int = 1
    const val EXTRA_MESSAGE: String = "com.thepascal.forecast.extra.MESSAGE"

    const val ZIP_CODE_REPLY: String = "com.thepascal.forecast.view.zipcode.REPLY"
    const val UNITS_REPLY: String = "com.thepascal.forecast.view.units.REPLY"

    const val VIEW_HOLDER_OK = 0
    const val VIEW_HOLDER_ERROR = 1
}