package com.rohit.kotlin.animalbio.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.rohit.kotlin.animalbio.R
import com.rohit.kotlin.animalbio.model.PersonModel

class PersonAdapter(private val personList: ArrayList<PersonModel>, private val appContext: Context): RecyclerView.Adapter<PersonAdapter.PersonHolder>() {
    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonAdapter.PersonHolder {
        val view = LayoutInflater.from(appContext).inflate(R.layout.row_view, parent, false)

        return PersonHolder(view)
    }

    override fun onBindViewHolder(holder: PersonAdapter.PersonHolder, position: Int) {
        holder.bindItem(personList[position])
    }

    inner class PersonHolder(private val personView: View): RecyclerView.ViewHolder(personView) {
        fun bindItem(person: PersonModel) {
            var name: TextView = personView.findViewById(R.id.tvNameView) as TextView
            var age: TextView = personView.findViewById(R.id.tvAgeView) as TextView
            name.text = person.name
            age.text = person.age

            personView.setOnClickListener {
                Toast.makeText(appContext, "Clicked on ${person.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}