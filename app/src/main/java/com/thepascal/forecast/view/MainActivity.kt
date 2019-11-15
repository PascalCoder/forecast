package com.thepascal.forecast.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.thepascal.forecast.R
import com.thepascal.forecast.models.ForecastList
import com.thepascal.forecast.presenter.CustomAdapter
import com.thepascal.forecast.presenter.Presenter
import com.thepascal.forecast.presenter.PresenterContract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewContract {

    private val TAG = MainActivity::class.java.simpleName

    companion object Constants {
        val UPDATE_REQUEST: Int = 1
        val EXTRA_MESSAGE: String = "com.thepascal.forecast.extra.MESSAGE"
    }

    lateinit var presenter: PresenterContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val linearLayoutManager = LinearLayoutManager(this)
        homeRecyclerView.layoutManager = linearLayoutManager

        presenter = Presenter()
        presenter.bindView(this)
        presenter.initializeRetrofit()
        presenter.getForecasts(Presenter.defaultZipCode, Presenter.defaultUnit)

        homeCityText.text = Presenter.city
        homeCondition.text = Presenter.condition

        homeSettingsImage.setOnClickListener {
            updateDetails(it)
        }
    }

    override fun addForecast(dataSet: ForecastList) {
        homeRecyclerView.adapter = CustomAdapter(dataSet)

        Log.d(TAG, "onCreate: ${Presenter.defaultZipCode} ${Presenter.defaultUnit} City: ${Presenter.city} Temp: ${Presenter.temp}")

        homeCityText.text = Presenter.city
        val temp: Double = (Presenter.temp).toDouble()
        homeTemp.text = "${Math.round(temp)} °"
        homeCondition.text = Presenter.condition

        if ((Presenter.temp).toDouble() < 60) {
            homeToolbar.setBackgroundColor(resources.getColor(R.color.colorSkyBlue))
        } else {
            homeToolbar.setBackgroundColor(resources.getColor(R.color.colorOrange))
        }
    }

    override fun onError(errorMessage: String) {
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, "onActivityResult: " + "Back to main before check!")
        if (requestCode == UPDATE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: " + "Back to main!")

                val zipCode = data?.getStringExtra(DetailsActivity.ZIP_CODE_REPLY)
                val units = data?.getStringExtra(DetailsActivity.UNITS_REPLY)

                Log.d(TAG, "onActivityResult: Details: $zipCode $units")

                presenter.bindView(this)
                presenter.initializeRetrofit()
                presenter.getForecasts(zipCode!!, units!!)
            }
        }
    }

    private fun updateDetails(view: View){

        val message = "Testing message"
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        startActivityForResult(intent, UPDATE_REQUEST)

        Log.d(TAG, "updateDetails: Details Activity started!")
    }
}
