package com.thepascal.forecast

fun formatTime(s: String): String {
    val fullTime = s.substring(s.indexOf(" ") + 1)
    var time: Int
    val meridian: String

    if (fullTime[0] != '0') {
        time = Integer.parseInt(fullTime.substring(0, fullTime.indexOf(':')))

        meridian = if (time >= 12) Constants.POST_MERIDIAN else Constants.ANTE_MERIDIAN

    } else {
        time = Integer.parseInt(fullTime.substring(1, fullTime.indexOf(':')))

        meridian = Constants.ANTE_MERIDIAN
    }

    if (time > 12 && meridian == Constants.POST_MERIDIAN) {
        time -= 12
    }
    if (time == 0 && meridian == Constants.ANTE_MERIDIAN) {
        time += 12
    }
    return "$time" + fullTime.substring(fullTime.indexOf(':'), fullTime.lastIndexOf(':')) + "  $meridian"
}

//"${Math.round(Presenter.lists[1][i].main.temp)}°"
fun formatTemperature(temp: Double): String {
    return "${Math.round(temp)}°"
}