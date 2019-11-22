package com.thepascal.forecast.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.thepascal.forecast.Constants.IMAGE_PATH
import com.thepascal.forecast.Constants.IMAGE_URL
import com.thepascal.forecast.formatTemperature
import com.thepascal.forecast.formatTime
import com.thepascal.forecast.models.Forecasts
import kotlinx.android.synthetic.main.item_layout.view.*

class CustomViewHolder(itemView: View) : BaseViewHolder(itemView) {

    fun bind(dailyForecast: ArrayList<Forecasts>, position: Int) {
        with(itemView) {
            val timeTextViews: Array<TextView> = arrayOf(
                itemTime1, itemTime2, itemTime3, itemTime4,
                itemTime5, itemTime6, itemTime7, itemTime8
            )
            val tempTextViews: Array<TextView> = arrayOf(
                itemTemp1, itemTemp2, itemTemp3, itemTemp4,
                itemTemp5, itemTemp6, itemTemp7, itemTemp8
            )
            val imageViews: Array<ImageView> = arrayOf(
                itemImage1, itemImage2, itemImage3, itemImage4,
                itemImage5, itemImage6, itemImage7, itemImage8
            )
            val tvDay: TextView = this.itemDay

            var dateTime = " "

            if (dailyForecast.isNotEmpty()) {
                dateTime = dailyForecast[position].dateTime
            }

            tvDay.text = dateTime.substring(0, dateTime.indexOf(" "))

            for (i in 0 until dailyForecast.size) {
                timeTextViews[i].text = formatTime(dailyForecast[i].dateTime)
                tempTextViews[i].text = formatTemperature(dailyForecast[i].main.temp)
                val img = dailyForecast[i].weather[0].icon
                Picasso.get().load(IMAGE_URL + img + IMAGE_PATH).into(imageViews[i])
            }
        }
    }
}