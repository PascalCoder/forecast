package com.thepascal.forecast

import com.thepascal.forecast.Constants.ANTE_MERIDIAN
import com.thepascal.forecast.Constants.POST_MERIDIAN

fun formatTime(s: String): String {
    val fullTime = s.substring(s.indexOf(" ") + 1)
    var time: Int
    val meridian: String

    if (fullTime[0] != '0') {
        time = Integer.parseInt(fullTime.substring(0, fullTime.indexOf(':')))

        meridian = if (time >= 12) POST_MERIDIAN else ANTE_MERIDIAN

    } else {
        time = Integer.parseInt(fullTime.substring(1, fullTime.indexOf(':')))

        meridian = ANTE_MERIDIAN
    }

    if (time > 12 && meridian == POST_MERIDIAN) {
        time -= 12
    }
    if (time == 0 && meridian == ANTE_MERIDIAN) {
        time += 12
    }
    return "$time" + fullTime.substring(fullTime.indexOf(':'), fullTime.lastIndexOf(':')) + "  $meridian"
}

fun formatTemperature(temp: Double): String {
    return "${Math.round(temp)}°"
}