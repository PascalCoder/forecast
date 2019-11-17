package com.thepascal.forecast.presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thepascal.forecast.Constants.IMAGE_PATH
import com.thepascal.forecast.Constants.IMAGE_URL
import com.thepascal.forecast.R
import com.thepascal.forecast.formatTemperature
import com.thepascal.forecast.formatTime
import com.thepascal.forecast.models.ForecastsData
import kotlinx.android.synthetic.main.item_layout.view.*

class CustomAdapter(var dataSet: ForecastsData) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    private val tag = CustomAdapter::class.java.simpleName
    private lateinit var context: Context

    init {
        dataSet = Presenter.forecastsData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)

        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var dateTime = " "

        if (Presenter.forecasts[position].isNotEmpty()) {
            dateTime = Presenter.forecasts[position][position].dateTime
        }

        /*if (position == 0) {
            holder.tvDay.text = context.getString(R.string.today_text)
        } else if (position == 1) {
            holder.tvDay.text = context.getString(R.string.tomorrow_text)
        } else {
            holder.tvDay.text = dateTime.substring(0, dateTime.indexOf(' ')) // why not " "
        }*/
        holder.tvDay.text = dateTime.substring(0, dateTime.indexOf(" "))

        for (i in 0 until Presenter.forecasts[position].size) {
            holder.timeTextViews[i].text = formatTime(Presenter.forecasts[position][i].dateTime)
            holder.tempTextViews[i].text = formatTemperature(Presenter.forecasts[position][i].main.temp)
            val img = Presenter.forecasts[position][i].weather[0].icon
            Picasso.get().load(IMAGE_URL + img + IMAGE_PATH).into(holder.imageViews[i])
        }

    }

    override fun getItemCount(): Int = 5 //number of days forecast


    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            context = itemView.context
        }

        var timeTextViews: Array<TextView> = arrayOf(
            itemView.itemTime1, itemView.itemTime2,
            itemView.itemTime3, itemView.itemTime4,
            itemView.itemTime5, itemView.itemTime6,
            itemView.itemTime7, itemView.itemTime8
        )
        var tempTextViews: Array<TextView> = arrayOf(
            itemView.itemTemp1, itemView.itemTemp2,
            itemView.itemTemp3, itemView.itemTemp4,
            itemView.itemTemp5, itemView.itemTemp6,
            itemView.itemTemp7, itemView.itemTemp8
        )
        var imageViews: Array<ImageView> = arrayOf(
            itemView.itemImage1, itemView.itemImage2,
            itemView.itemImage3, itemView.itemImage4,
            itemView.itemImage5, itemView.itemImage6,
            itemView.itemImage7, itemView.itemImage8
        )
        var tvDay: TextView = itemView.itemDay

    }
}