package com.rohit.kotlin.firebasebasics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseRef = firebaseDatabase.getReference("messages").push()

        mAuth = FirebaseAuth.getInstance()

        // Create new user
        val email = emailId.text.toString().trim()
        val password = passwordId.text.toString().trim()
        val btn = createAccountBtn
        btn.setOnClickListener {
            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                task: Task<AuthResult> ->
                    if(task.isSuccessful) {
                        val user: FirebaseUser = mAuth!!.currentUser!!
                        Log.d("ROHIT", "New user : " + user.email.toString())
                    } else {
                        Log.d("ROHIT", "New user not created..!! Exception : " + task.exception.toString())
                    }
            }
        }


        // Sign in existing user
//        mAuth!!.signInWithEmailAndPassword("rohit@test.com", "Test123#").addOnCompleteListener {
//            task: Task<AuthResult> ->
//                if (task.isSuccessful) {
//                    Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()
//                } else {
//                    Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show()
//                }
//
//        }

//        val employee = Employee("James Bond", "Developer", "Home street 1123", 23)
//
//        databaseRef.setValue(employee)
//
//
//        // Read from Firebase DB
//        databaseRef.addValueEventListener(object: ValueEventListener{
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("ROHIT", "Error in firebase access : " + error.message)
//            }
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val value = dataSnapshot.value as HashMap<String, Any>
//                Log.d("ROHIT", " Object data : " + value.get("name"))
//            }
//        })
    }

    override fun onStart() {
        super.onStart()
        currentUser = mAuth?.currentUser

        if(currentUser != null) {
            Toast.makeText(this, "User is logged in", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "User is not logged in", Toast.LENGTH_LONG).show()
        }
    }

    data class  Employee(var name: String, var position: String, var adress: String, var age: Int) {

    }
}
