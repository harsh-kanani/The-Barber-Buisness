package com.example.thebarberbuisness

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*


class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        lblreg.setOnClickListener {
            startActivity(Intent(this@Login,Registration::class.java))

        }
        btnlogin.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("Shop")
            var myRef1=myRef.child("${txtunm.text.toString()}")
            myRef1.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) { // This method is called once with the initial value and again
// whenever data at this location is updated.
                    val value =dataSnapshot.child("password").value
                    if(value!!.equals(txtpwd.text.toString())){
                        var sp=getSharedPreferences("MySp",Activity.MODE_PRIVATE)
                        var edt = sp.edit()
                        edt.putString("unm","${txtunm.text.toString()}")
                        edt.apply()
                        edt.commit()
                        startActivity(Intent(this@Login,Dashboard::class.java))
                        finish()
                    }

                    Log.d("data", "Value is: $value")
                }

                override fun onCancelled(error: DatabaseError) { // Failed to read value
                    //Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
                }
            })

        }
    }
}
