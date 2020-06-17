package com.example.thebarberbuisness

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_view_category.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*
import javax.xml.datatype.DatatypeConstants
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList


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
                else
                    swstatus.isChecked = false

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

        val database1 = FirebaseDatabase.getInstance()
        val myref = database1.getReference("appinment")

        var myref1=myref.child(unm.toString())
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        val formatted = current.format(formatter)
        var tommorw = current.plusDays(1)
        var form = tommorw.format(formatter)



        var arlst:ArrayList<AppointmentData> = arrayListOf<AppointmentData>()
        fun f(dt:String){
            var myref2=myref1.child(dt)

            myref2.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) { // This method is called once with the initial value and again
// whenever data at this location is updated.
                    arlst.clear()
                    for(s1 in dataSnapshot.children){
                        var value = s1.getValue(AppointmentData::class.java)
                        if(value != null){
                            arlst.add(value)
                            //Log.d("hello",value.toString())
                        }

                    }
                    if(arlst.size>0){
                        arlst.removeAt(arlst.size-1)
                    }

                    var ad = AppointmentMainClass(this@Dashboard,arlst,unm.toString())
                    rcv.adapter=ad
                    rcv.layoutManager = LinearLayoutManager(this@Dashboard,LinearLayoutManager.VERTICAL,false)
                }




                override fun onCancelled(error: DatabaseError) { // Failed to read value
                    //Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
                }

            })

        }
        f(formatted.toString())
        swtoday.setOnCheckedChangeListener { buttonView, isChecked ->


            var msg= if(isChecked) "not" else "${formatted.toString()}"



            // Write a message to the database

            if(msg != "not"){
                f(formatted.toString())
            }
            else{
                f(form.toString())
            }



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
                var sp = getSharedPreferences("MySp",Activity.MODE_PRIVATE)
                var edt =sp.edit()
                edt.putString("unm",null)
                edt.apply()
                edt.commit()
                startActivity(Intent(this@Dashboard,Login::class.java))

                return true
            }
            else->super.onOptionsItemSelected(item)
        }

    }
}







