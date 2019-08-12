package com.rohit.kotlin.choreapp.data

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rohit.kotlin.choreapp.R
import com.rohit.kotlin.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.view.*

class ChoreListAdapter(private val context: Context, private val list: ArrayList<Chore>): RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoreListAdapter.ViewHolder {
        // Create view from XML file
        val view = LayoutInflater.from(context).inflate(R.layout.list_row_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChoreListAdapter.ViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val choreName = itemView.findViewById(R.id.tvChoreName) as TextView
        val assignedBy = itemView.findViewById(R.id.tvAssignedBy) as TextView
        val assignedTo = itemView.findViewById(R.id.tvAssignedTo) as TextView
        val assignedDate = itemView.findViewById(R.id.tvDateAssigned) as TextView

        fun bindView(chore: Chore) {
            choreName.text = chore.choreName
            assignedBy.text = chore.assignedBy
            assignedTo.text = chore.assignedTo
            assignedDate.text = chore.getFormattedDateString()
        }
    }
}