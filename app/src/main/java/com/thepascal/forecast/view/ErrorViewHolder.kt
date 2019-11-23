package com.thepascal.forecast.view

import android.view.View
import com.thepascal.forecast.R
import kotlinx.android.synthetic.main.item_error_layout.view.*

class ErrorViewHolder(itemView: View) : BaseViewHolder(itemView) {

    fun bind() {
        with(itemView){
            itemErrorMessage.text = context.getString(R.string.no_data_error_msg)
        }
    }
}