package com.rohit.kotlin.choreapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rohit.kotlin.choreapp.R
import com.rohit.kotlin.choreapp.data.ChoreListAdapter
import com.rohit.kotlin.choreapp.data.ChoresDBHandler
import com.rohit.kotlin.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.popup_dialog.view.*

class ChoreListActivity : AppCompatActivity() {

    private var dbHandler: ChoresDBHandler? = null
    private var adapter: ChoreListAdapter? = null
    private var choreList: ArrayList<Chore>? = null
    private var savedChoreList: ArrayList<Chore>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var dialogBuilder: AlertDialog.Builder ? = null
    private var alertDialog: AlertDialog? = null

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

        if(dbHandler!!.getChoresCount() > 0) {
            // Load chores from DB
            choreList = dbHandler!!.readAllChores()
            for (ch in choreList!!.iterator()) {
                val chore: Chore = Chore()
                chore.choreName = ch.choreName
                chore.assignedTo = ch.assignedTo
                chore.assignedBy = ch.assignedBy
                chore.timeAssigned = ch.timeAssigned

                savedChoreList!!.add(chore)
            }
            adapter!!.notifyDataSetChanged()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_menu_button) {
            Log.d("Menu Clicked", "Add menu clicked..!")
            createPopupDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    fun createPopupDialog() {
        val view: View = layoutInflater.inflate(R.layout.popup_dialog, null)
        val choreName = view.etPopupEnterChore
        val assignedBy = view.etPopupAssignedBy
        val assignedTo = view.etPopupAssignedTo
        val btnSave = view.btnPopupSave

        dialogBuilder = AlertDialog.Builder(this).setView(view)
        alertDialog = dialogBuilder!!.create()
        alertDialog?.show()

        btnSave.setOnClickListener {
            val name = choreName.text.toString().trim()
            val aBy = assignedBy.text.toString().trim()
            val aTo = assignedTo.text.toString().trim()
            if(!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(aBy)
                && !TextUtils.isEmpty(aTo)) {
                val chore = Chore()
                chore.choreName = name
                chore.assignedBy = aBy
                chore.assignedTo = aTo
                dbHandler!!.createChore(chore)
            } else {
                Toast.makeText(this, "Enter all values", Toast.LENGTH_LONG).show()
            }

            alertDialog!!.dismiss()
        }
    }
}
