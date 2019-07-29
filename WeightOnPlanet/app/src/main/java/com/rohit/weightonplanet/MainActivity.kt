package com.rohit.weightonplanet

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val marsGravity = 0.38
    val venusGravity = 0.91
    val jupiterGravity = 2.34

    var weight:Editable? = null
    var selectedCheckBox: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShowWeight.setOnClickListener(this)
        checkMars.setOnClickListener(this)
        checkVenus.setOnClickListener(this)
        checkJupiter.setOnClickListener(this)

        weight = enterWeightId.text
    }

    private fun calculateWeight(checkBox: CheckBox) { // 89.78
        selectedCheckBox = checkBox
        if(weight!!.isEmpty()) {
            Toast.makeText(this, "Please enter your weight!!", Toast.LENGTH_LONG).show()
        } else {
            var userWeight: Double = weight.toString().toDouble()
            var result: Double = userWeight * marsGravity
            when (checkBox.id) {
                R.id.checkMars -> {
                    result = userWeight * marsGravity
                    var marsText = getString(R.string.mars_message) + " $result"
                    tvResultWeight.text = marsText
                }
                R.id.checkVenus -> {
                    result = userWeight * venusGravity
                    var venusText = getString(R.string.venus_message) + " $result"
                    tvResultWeight.text = venusText
                }
                R.id.checkJupiter -> {
                    result = userWeight * jupiterGravity
                    var jupiterText = getString(R.string.jupiter_message) + " $result"
                    tvResultWeight.text = jupiterText
                }
                else ->  {
                    tvResultWeight.text = getString(R.string.mars_message) + " $result"
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when(view!!.id) {
            R.id.btnShowWeight -> {
                if(weight!!.isEmpty() || selectedCheckBox == null) {
                    Toast.makeText(this, "Enter your weight & select planet!!", Toast.LENGTH_LONG).show()
                } else {
                    calculateWeight(selectedCheckBox!!)
                }
            }
            else -> {
                view as CheckBox
                var isChecked: Boolean = view.isChecked

                when(view.id) {
                    R.id.checkMars -> {
                        if(isChecked) {
                            calculateWeight(view)
                            checkVenus.isChecked = false
                            checkJupiter.isChecked = false
                        }
                    }
                    R.id.checkVenus -> {
                        if(isChecked) {
                            calculateWeight(view)
                            checkMars.isChecked = false
                            checkJupiter.isChecked = false
                        }
                    }
                    R.id.checkJupiter -> {
                        if(isChecked) {
                            calculateWeight(view)
                            checkVenus.isChecked = false
                            checkMars.isChecked = false
                        }
                    }
                }
            }

        }
    }
}
