package com.thepascal.forecast.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.thepascal.forecast.Constants.DEFAULT_SYSTEM
import com.thepascal.forecast.Constants.DEFAULT_ZIP_CODE
import com.thepascal.forecast.Constants.EXTRA_MESSAGE
import com.thepascal.forecast.Constants.UNITS_REPLY
import com.thepascal.forecast.Constants.UPDATE_REQUEST
import com.thepascal.forecast.Constants.ZIP_CODE_REPLY
import com.thepascal.forecast.R
import com.thepascal.forecast.formatTemperature
import com.thepascal.forecast.models.ForecastsData
import com.thepascal.forecast.presenter.CustomAdapter
import com.thepascal.forecast.presenter.Presenter
import com.thepascal.forecast.presenter.PresenterContract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewContract {

    private val tag = MainActivity::class.java.simpleName

    private lateinit var presenter: PresenterContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val linearLayoutManager = LinearLayoutManager(this)
        homeRecyclerView.layoutManager = linearLayoutManager

        presenter = Presenter()
        presenter.bindView(this)
        presenter.initializeRetrofit()
        presenter.getForecasts(DEFAULT_ZIP_CODE, DEFAULT_SYSTEM)

        homeCityText.text = Presenter.city
        homeCondition.text = Presenter.condition

        homeSettingsImage.setOnClickListener {
            updateDetails()
        }
    }

    override fun addForecast(dataSet: ForecastsData) {
        homeErrorMessage.visibility = View.GONE
        homeRecyclerView.visibility = View.VISIBLE
        homeRecyclerView.adapter = CustomAdapter(dataSet)

        Log.d(tag, "onCreate: $DEFAULT_ZIP_CODE $DEFAULT_SYSTEM City: ${Presenter.city} Temp: ${Presenter.temp}")

        homeCityText.text = Presenter.city
        val temp: Double = (Presenter.temp).toDouble()
        homeTemp.text = formatTemperature(temp)
        homeCondition.text = Presenter.condition

        if ((Presenter.temp).toDouble() < 60) {
            homeToolbar.setBackgroundColor(resources.getColor(R.color.colorSkyBlue))
        } else {
            homeToolbar.setBackgroundColor(resources.getColor(R.color.colorOrange))
        }
    }

    override fun onError(errorMessage: String) {
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
        homeRecyclerView.visibility = View.GONE
        homeErrorMessage.visibility = View.VISIBLE
        homeErrorMessage.text = errorMessage
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(tag, "onActivityResult: " + "Back to main before check!")
        if (requestCode == UPDATE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.d(tag, "onActivityResult: " + "Back to main!")

                val zipCode: String? = data?.getStringExtra(ZIP_CODE_REPLY)
                val units: String? = data?.getStringExtra(UNITS_REPLY)

                Log.d(tag, "onActivityResult: Details: $zipCode $units")

                presenter.bindView(this)
                presenter.initializeRetrofit()
                if(zipCode != null && units != null){
                    presenter.getForecasts(zipCode, units)

                    if(Presenter.forecasts[0].isEmpty()){
                        this.onError("No data found for the provided zipcode")
                    }
                }

            }
        }
    }

    private fun updateDetails(){

        val message = "Testing message"
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        startActivityForResult(intent, UPDATE_REQUEST)

        Log.d(tag, "updateDetails: Details Activity started!")
    }
}
