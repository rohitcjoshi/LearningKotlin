package com.rohit.kotlin.volleytest.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.rohit.kotlin.volleytest.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSearch.setOnClickListener {
            startActivity(Intent(this, RecipeListActivity::class.java))
        }

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var dbReference = firebaseDatabase.reference
        dbReference.setValue("Hello Firebase")
    }
}
