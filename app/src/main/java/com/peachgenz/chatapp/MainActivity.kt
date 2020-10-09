package com.peachgenz.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var database = FirebaseDatabase.getInstance()
        var myRef = database.getReference("user")

        var employee = Employee("Srithep", "Witayapanpracha", "192/72", 23)
        myRef.setValue(employee)
        
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                var value = p0.value as HashMap<String, Any>
                Log.d("VALUE: ", value.get("firstName").toString())
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("VALUE: ", p0.toString())
            }

        })
    }

    data class Employee(var firstName: String, var lastName: String, var address: String, var age: Int )
}