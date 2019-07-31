package com.rohit.kotlin.choreapp.model

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
}