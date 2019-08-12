package com.rohit.kotlin.choreapp.model

import java.text.DateFormat
import java.util.*

class Chore() {
    var choreName: String? = null
    var assignedBy: String? = null
    var assignedTo: String? = null
    var id: Int? = null
    var timeAssigned: Long? = null

    constructor(choreName: String, assignedBy: String, assignedTo: String, timeAssigned: Long, id: Int): this() {
        this.choreName = choreName
        this.assignedBy = assignedBy
        this.assignedTo = assignedTo
        this.timeAssigned = timeAssigned
        this.id = id
    }

    fun getFormattedDateString(): String {
        val dateFormat: DateFormat = DateFormat.getDateInstance()
        // Formatted Date: Feb 24, 2019
        return dateFormat.format(timeAssigned?.let { Date(it).time })
    }
}