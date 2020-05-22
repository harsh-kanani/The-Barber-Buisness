package com.example.thebarberbuisness

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.internal.Objects
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_other_info.*


class otherInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        var arr:Array<String> = arrayOf("Gents","Ladies","Unisex")
        var ad:ArrayAdapter<String> = ArrayAdapter(this@otherInfo,android.R.layout.simple_spinner_dropdown_item,arr)
        sptp.adapter=ad

        var sp=getSharedPreferences("MySp",Activity.MODE_PRIVATE)
        var unm:String?=sp.getString("unm",null)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Shop")
        var myRef1 = myRef.child("${unm.toString()}")

        var email =""
        var mobile =""
        var password=""
        var uname=""
        var flag=0
        myRef1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) { // This method is called once with the initial value and again
// whenever data at this location is updated.
                uname = dataSnapshot.child("userName").value.toString()
                email = dataSnapshot.child("email").value.toString()
                mobile = dataSnapshot.child("mobile").value.toString()
                password = dataSnapshot.child("password").value.toString()
                var value=dataSnapshot.child("oname").value.toString()
                Log.d("long",value)
                if(value == " "){
                    flag=1
                }
                else{
                    startActivity(Intent(this@otherInfo,Dashboard::class.java))
                    finish()
                }
                Log.d("long",flag.toString())

            }

            override fun onCancelled(error: DatabaseError) { // Failed to read value
                //Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })


        btnsave.setOnClickListener {

                var shop=ShopData(uname,email,mobile,txtshopnm.text.toString(),txtct.text.toString(),password,sptp.selectedItem.toString(),txtoname.text.toString(),"Close"," ",txtaddress.text.toString())
                myRef.child(uname).setValue(shop).addOnCompleteListener {
                    Toast.makeText(this@otherInfo,"Successfully Save",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@otherInfo,Login::class.java))
                    finish()
                }

        }
    }
}
