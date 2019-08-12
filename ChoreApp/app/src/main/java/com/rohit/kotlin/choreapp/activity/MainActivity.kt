package com.rohit.kotlin.choreapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ProgressBar
import android.widget.Toast
import com.rohit.kotlin.choreapp.R
import com.rohit.kotlin.choreapp.data.ChoresDBHandler
import com.rohit.kotlin.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dbHandler: ChoresDBHandler? = null
    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = ProgressBar(this)

        dbHandler = ChoresDBHandler(this)

        btnSave.setOnClickListener {
            if(!TextUtils.isEmpty(etEnterChore.text) && !TextUtils.isEmpty(etAssignedTo.text) && !TextUtils.isEmpty(etAssignedBy.text)) {
                // Save to database
                val chore = Chore()
                chore.choreName = etEnterChore.text.toString()
                chore.assignedTo = etAssignedTo.text.toString()
                chore.assignedBy = etAssignedBy.text.toString()

                saveToDatabase(chore)

                startActivity(Intent(this, ChoreListActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Enter all values", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun saveToDatabase(chore: Chore) {
        dbHandler!!.createChore(chore)
    }
}
