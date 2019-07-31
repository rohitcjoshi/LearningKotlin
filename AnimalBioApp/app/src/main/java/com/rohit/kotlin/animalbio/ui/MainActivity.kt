package com.rohit.kotlin.animalbio.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rohit.kotlin.animalbio.R
import com.rohit.kotlin.animalbio.data.PersonAdapter
import com.rohit.kotlin.animalbio.model.PersonModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var personAdapter: PersonAdapter? = null
    private var personList: ArrayList<PersonModel>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        personList = ArrayList()
        layoutManager = LinearLayoutManager(this)
        personAdapter = PersonAdapter(personList!!, this)

        // Setup list
        recycleView.layoutManager = layoutManager
        recycleView.adapter = personAdapter

        // Add data
        for(i in 0..9) {
            val person = PersonModel()
            person.name = "Rohit " + i
            person.age = "" + (i+20)
            personList!!.add(person)
        }

        personAdapter!!.notifyDataSetChanged()
    }
}
