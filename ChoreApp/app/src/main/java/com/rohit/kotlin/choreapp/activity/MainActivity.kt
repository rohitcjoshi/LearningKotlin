package com.rohit.kotlin.choreapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rohit.kotlin.choreapp.R
import com.rohit.kotlin.choreapp.data.ChoresDBHandler
import com.rohit.kotlin.choreapp.model.Chore

class MainActivity : AppCompatActivity() {

    var dbHandler: ChoresDBHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ChoresDBHandler(this)

        var chore = Chore()
    }
}
