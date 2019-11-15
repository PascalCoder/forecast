package com.thepascal.forecast.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.thepascal.forecast.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private val TAG = DetailsActivity::class.java.simpleName

    companion object Constants {
        val ZIP_CODE_REPLY: String = "com.thepascal.forecast.view.zipcode.REPLY"
        val UNITS_REPLY: String = "com.thepascal.forecast.view.units.REPLY"

        var zipCode: String = ""
        var unit: String = ""
        var system: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.hide()
        val message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE)

        val arrayAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this, R.array.unit, android.R.layout.simple_spinner_item
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        detailsSpinner.adapter = arrayAdapter
        detailsSpinner.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                unit = detailsSpinner.selectedItem.toString()
                if (unit.equals("Celsius")) {
                    system = "metric"
                } else {
                    system = "imperial"
                }
                zipCode = detailsZipCode.text.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                system = "imperial"
                zipCode = detailsZipCode.text.toString()
            }
        })

        if (unit == "Celsius") {
            unit = "metric"
        } else {
            unit = "imperial"
        }

        detailsBackArrow.setOnClickListener {
            Toast.makeText(applicationContext, "Unit: $unit \n System: $system", Toast.LENGTH_LONG).show()
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        Log.d(TAG, "onBackPressed: Inside onBackPressed!")

        val replyIntent = Intent()
        replyIntent.putExtra(ZIP_CODE_REPLY, zipCode)
        replyIntent.putExtra(UNITS_REPLY, system)

        setResult(Activity.RESULT_OK, replyIntent)

        finish()
    }
}
