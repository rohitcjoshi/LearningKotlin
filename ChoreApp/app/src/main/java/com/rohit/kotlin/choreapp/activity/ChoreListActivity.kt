package com.rohit.kotlin.choreapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rohit.kotlin.choreapp.R
import com.rohit.kotlin.choreapp.data.ChoreListAdapter
import com.rohit.kotlin.choreapp.data.ChoresDBHandler
import com.rohit.kotlin.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*

class ChoreListActivity : AppCompatActivity() {

    private var dbHandler: ChoresDBHandler? = null
    private var adapter: ChoreListAdapter? = null
    private var choreList: ArrayList<Chore>? = null
    private var savedChoreList: ArrayList<Chore>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        dbHandler = ChoresDBHandler(this)

        choreList = ArrayList()
        savedChoreList = ArrayList()

        layoutManager = LinearLayoutManager(this)
        adapter = ChoreListAdapter(this, savedChoreList!!)

        // Setup recycle view
        choresListView.layoutManager = layoutManager
        choresListView.adapter = adapter

        // Load chores from DB
        choreList = dbHandler!!.readAllChores()
        for(ch in choreList!!.iterator()) {
            val chore: Chore = Chore()
            chore.choreName = ch.choreName
            chore.assignedTo = ch.assignedTo
            chore.assignedBy = ch.assignedBy
            chore.timeAssigned = ch.timeAssigned

            savedChoreList!!.add(chore)
        }
        adapter!!.notifyDataSetChanged()
    }
}
