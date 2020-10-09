package com.peachgenz.chatapp

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
    private var mAuth: FirebaseAuth ?= null
    private var currentUser : FirebaseUser ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var database = FirebaseDatabase.getInstance()
        var myRef = database.getReference("user").push()

        mAuth = FirebaseAuth.getInstance()

        btn_create.setOnClickListener {
            var email = et_email.text.toString().trim()
            var password = et_password.text.toString().trim()

            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                task: Task<AuthResult> ->
                if (task.isSuccessful){
                    Toast.makeText(this, "Create successful", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Create unsuccessful", Toast.LENGTH_LONG).show()
                }
            }
        }

        btn_login.setOnClickListener {
            var email = et_email.text.toString().trim()
            var password = et_password.text.toString().trim()
            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                task: Task<AuthResult> ->
                if (task.isSuccessful){
                    Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Login unsuccessful", Toast.LENGTH_LONG).show()
                }
            }
        }

        var employee = Employee("Srithep", "Witayapanpracha", "45/370", 23)
        myRef.setValue(employee)
        
//        myRef.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(p0: DataSnapshot) {
//                var value = p0.value as HashMap<String, Any>
//                Log.d("VALUE: ", value.get("firstName").toString())
//            }
//
//            override fun onCancelled(p0: DatabaseError) {
//                Log.d("VALUE: ", p0.toString())
//            }
//
//        })
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.signOut()
        currentUser = mAuth!!.currentUser
        if(currentUser != null){
            Toast.makeText(this, "Already logged in", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Please login", Toast.LENGTH_LONG).show()
        }
    }

    data class Employee(var firstName: String, var lastName: String, var address: String, var age: Int )
}