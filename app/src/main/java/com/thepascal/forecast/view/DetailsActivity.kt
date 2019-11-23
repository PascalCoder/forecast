package com.thepascal.forecast.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.thepascal.forecast.Constants.DEFAULT_SYSTEM
import com.thepascal.forecast.Constants.METRIC_SYSTEM
import com.thepascal.forecast.Constants.UNITS_REPLY
import com.thepascal.forecast.Constants.ZIP_CODE_REPLY
import com.thepascal.forecast.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private val tag = DetailsActivity::class.java.simpleName

    companion object Constants {
        var zipCode: String = ""
        var unit: String = ""
        var system: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.hide()

        zipCode = detailsZipCode.editText?.text.toString()

        val arrayAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this, R.array.unit, android.R.layout.simple_spinner_item
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        detailsSpinner.adapter = arrayAdapter
        detailsSpinner.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                unit = detailsSpinner.selectedItem.toString()
                system = if (unit == "Celsius") {
                     METRIC_SYSTEM
                } else {
                    DEFAULT_SYSTEM
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                system = DEFAULT_SYSTEM
            }
        })

        detailsBackArrow.setOnClickListener {
            //Toast.makeText(applicationContext, "Unit: $unit \n System: $system \n ZipCode: $zipCode", Toast.LENGTH_LONG).show()
            onBackPressed()
        }

        detailsSubmitButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        Log.d(tag, "onBackPressed: Inside onBackPressed!")
        zipCode = detailsZipCode.editText?.text.toString()

        if (zipCode.isBlank() || zipCode.length != 5){
            return
        }

        val replyIntent = Intent()
        replyIntent.putExtra(ZIP_CODE_REPLY, zipCode)
        replyIntent.putExtra(UNITS_REPLY, system)

        setResult(Activity.RESULT_OK, replyIntent)

        finish()
    }
}
