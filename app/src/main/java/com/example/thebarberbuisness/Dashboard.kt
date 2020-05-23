package com.example.thebarberbuisness

import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class Dashboard : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Shop")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        var sp = getSharedPreferences("MySp",Activity.MODE_PRIVATE)
        var unm = sp.getString("unm","null")

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Shop")
        var myRef1=myRef.child(unm.toString())

        myRef1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) { // This method is called once with the initial value and again
// whenever data at this location is updated.
                val value =dataSnapshot.child("status").value
                if(value.toString().equals("Open")){
                    swstatus.isChecked = true
                }
                //Log.d(FragmentActivity.TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) { // Failed to read value
                //Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })

        swstatus.setOnCheckedChangeListener { buttonView, isChecked ->
            var msg = if(isChecked) "Open" else "Close"

            /*if(msg == "Open"){
                val current = LocalDateTime.now()

                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                val formatted = current.format(formatter)

                myRef.child(unm.toString()).child("openingTime").setValue(formatted.toString())

            }*/
            myRef.child(unm.toString()).child("status").setValue(msg).addOnCompleteListener {
                Toast.makeText(this@Dashboard,"Your Shop  is "+msg,Toast.LENGTH_LONG).show()
            }


        }

        btnchk.setOnClickListener {
            var dialog = Dialog(this@Dashboard)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            //dialog.setContentView()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menuaddcat->{
                startActivity(Intent(this@Dashboard,AddCategory::class.java))

                return true
            }
            R.id.menuviewcat->{
                startActivity(Intent(this@Dashboard,ViewCategory::class.java))

                return true
            }
            R.id.menulg->{
                startActivity(Intent(this@Dashboard,Login::class.java))

                return true
            }
            else->super.onOptionsItemSelected(item)
        }

    }
}
