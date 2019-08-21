package com.rohit.kotlin.choreapp.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.rohit.kotlin.choreapp.R
import com.rohit.kotlin.choreapp.activity.ChoreListActivity
import com.rohit.kotlin.choreapp.activity.MainActivity
import com.rohit.kotlin.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.view.*
import kotlinx.android.synthetic.main.popup_dialog.view.*

class ChoreListAdapter(private val context: Activity, private val list: ArrayList<Chore>): RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoreListAdapter.ViewHolder {
        // Create view from XML file
        val view = LayoutInflater.from(context.applicationContext).inflate(R.layout.list_row_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChoreListAdapter.ViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val choreName = itemView.findViewById(R.id.tvChoreName) as TextView
        val assignedBy = itemView.findViewById(R.id.tvAssignedBy) as TextView
        val assignedTo = itemView.findViewById(R.id.tvAssignedTo) as TextView
        val assignedDate = itemView.findViewById(R.id.tvDateAssigned) as TextView

        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete) as ImageButton
        val btnEdit = itemView.findViewById(R.id.btnEdit) as ImageButton


        fun bindView(chore: Chore) {
            choreName.text = "Chore: ${chore.choreName}"
            assignedBy.text = "Assigned By: ${chore.assignedBy}"
            assignedTo.text = "Assigned To: ${chore.assignedTo}"
            assignedDate.text = chore.getFormattedDateString()

            btnDelete.setOnClickListener(this)
            btnEdit.setOnClickListener(this)
        }

        override fun onClick(clickableView: View?) {
            val position: Int = adapterPosition
            val chore: Chore = list[position]
            when(clickableView!!.id) {
                btnDelete.id -> {
                    val db: ChoresDBHandler = ChoresDBHandler(context)
                    db.deleteChore(chore.id!!)
                    list.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    if(itemCount <= 0) {
                        context.startActivity(Intent(context, MainActivity::class.java))
                        context.finish()
                    }
                }
                btnEdit.id -> {
                    editChore(chore)
                }
            }
        }

        fun editChore(chore: Chore?) {
            val dbHandler: ChoresDBHandler = ChoresDBHandler(context)
            var dialogBuilder: AlertDialog.Builder?
            var alertDialog: AlertDialog?

            val view: View = LayoutInflater.from(context).inflate(R.layout.popup_dialog, null)
            val choreName = view.etPopupEnterChore
            choreName.setText(chore?.choreName)
            val assignedBy = view.etPopupAssignedBy
            assignedBy.setText(chore?.assignedBy)
            val assignedTo = view.etPopupAssignedTo
            assignedTo.setText(chore?.assignedTo)
            val btnSave = view.btnPopupSave

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            alertDialog = dialogBuilder!!.create()
            alertDialog?.setCancelable(true)
            alertDialog?.show()

            btnSave.setOnClickListener {
                val name = choreName.text.toString().trim()
                val aBy = assignedBy.text.toString().trim()
                val aTo = assignedTo.text.toString().trim()
                if(!TextUtils.isEmpty(name)
                    && !TextUtils.isEmpty(aBy)
                    && !TextUtils.isEmpty(aTo)) {
                    chore?.choreName = name
                    chore?.assignedBy = aBy
                    chore?.assignedTo = aTo
                    dbHandler.updateChore(chore!!)
                    alertDialog!!.dismiss()
                    notifyItemChanged(adapterPosition)
                } else {
                    Toast.makeText(context, "Enter all values", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}